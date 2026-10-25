package com.ecommerce.main.order.service.impl;

import com.ecommerce.main.address.entity.Address;
import com.ecommerce.main.address.respositroy.AddressRepository;
import com.ecommerce.main.cart.entity.Cart;
import com.ecommerce.main.cart.repositroy.CartRepositroy;
import com.ecommerce.main.common.exception.AddressNotFoundException;
import com.ecommerce.main.common.exception.EmptyCartException;
import com.ecommerce.main.common.exception.InvalidOrderStatusException;
import com.ecommerce.main.common.exception.OrderNotFoundException;
import com.ecommerce.main.order.dto.OrderResponse;
import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.order.entity.OrderStatus;
import com.ecommerce.main.order.mapper.OrderMapper;
import com.ecommerce.main.order.repository.OrderRepository;
import com.ecommerce.main.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.ecommerce.main.order.entity.OrderStatus.CANCELLED;
import static com.ecommerce.main.order.entity.OrderStatus.PENDING_PAYMENT;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final AddressRepository addressRepository;
    private final CartRepositroy cartRepositroy;

    @Override
    public OrderResponse create(Long userId, Long addressId) {
        Cart cart = cartRepositroy.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("سبد خرید موجوذ نیست"));
        if (cart.getItems().size() == 0) {
            throw new EmptyCartException();
        }

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException());

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .userId(userId)
                .status(OrderStatus.PENDING_PAYMENT)
                .shippingAddress(mapper.toShippingAddress(address))
                .totalPrice(BigDecimal.ZERO)
                .build();

        ///transfer cartItem to orderItem
        cart.getItems().forEach(cartItem -> order.addItem(mapper.toOrderItem(cartItem)));

        ///get total price of all orderitems//
        order.setTotalPrice(order.getItems().stream()
                .map(items -> items.totalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        Order saved = orderRepository.save(order);
        cart.getItems().clear();
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(Long userId) {
        return mapper.toResponses(orderRepository.findAllByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findOne(Long userId, Long id) {
        Order order = getOrder(userId, id);
        return mapper.toResponse(order);
    }

    @Override
    public OrderResponse cancel(Long userId, Long id) {
        Order order = getOrder(userId, id);
        if (order.getStatus() != PENDING_PAYMENT) {
            throw new InvalidOrderStatusException();
        }
        order.setStatus(CANCELLED);
        return mapper.toResponse(order);

    }

    private Order getOrder(Long userId, Long orderId) {
        return orderRepository.findByUserIdAndId(userId, orderId).orElseThrow(OrderNotFoundException::new);
    }
}
