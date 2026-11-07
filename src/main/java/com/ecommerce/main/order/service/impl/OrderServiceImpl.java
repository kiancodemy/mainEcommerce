package com.ecommerce.main.order.service.impl;

import com.ecommerce.main.address.entity.Address;
import com.ecommerce.main.address.respositroy.AddressRepository;
import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.repository.BookRepository;
import com.ecommerce.main.cart.entity.Cart;
import com.ecommerce.main.cart.repositroy.CartRepositroy;
import com.ecommerce.main.common.exception.*;
import com.ecommerce.main.order.dto.OrderResponse;
import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.order.entity.OrderItem;
import com.ecommerce.main.order.entity.OrderStatus;
import com.ecommerce.main.order.mapper.OrderMapper;
import com.ecommerce.main.order.repository.OrderRepository;
import com.ecommerce.main.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    private final BookRepository bookRepository;

    @Override
    public OrderResponse create(Long userId, Long addressId) {
        Cart cart = cartRepositroy.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("سبد خرید موجوذ نیست"));
        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException();
        }

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException());

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .userId(userId)
                .expiredAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .status(OrderStatus.PENDING_PAYMENT)
                .shippingAddress(mapper.toShippingAddress(address))
                .totalPrice(BigDecimal.ZERO)
                .build();

        ///transfer cartItem to orderItem
        cart.getItems().forEach(cartItem -> order.addItem(mapper.toOrderItem(cartItem)));

        ///update book stock before making new order//
        order.getItems().forEach(orders->updateBookStockBeforePaying(orders));

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
        /// update book stock if he refuse to pay so others can buy /////
        order.getItems().forEach(item->updateBookStockIfNotPaid(item));
        order.setStatus(CANCELLED);
        return mapper.toResponse(order);

    }

    private Order getOrder(Long userId, Long orderId) {
        return orderRepository.findByUserIdAndId(userId, orderId).orElseThrow(OrderNotFoundException::new);
    }

    //// update number of book in stock before confirming the order////
    private void updateBookStockBeforePaying(OrderItem orderItem) {
        Long id = orderItem.getBook().getId();
        Book byIdLocked = bookRepository.findByIdLocked(id).orElseThrow(()->new UsernameNotFoundException("کتاب یافت نشد"));
        int stock = byIdLocked.getStock();
        boolean ifAvailable = stock >= orderItem.getQuantity();
        if (!ifAvailable) {
            throw new ResourceNotFoundException(
                    "موجودی کتاب «" + orderItem.getTitle() + "» کافی نیست"
            );
        }
        byIdLocked.setStock(byIdLocked.getStock() - orderItem.getQuantity());
        bookRepository.save(byIdLocked);


    }


    //// update number of book in stock if user forgot to pay after limited amount of time or canceled the payment////
    private void updateBookStockIfNotPaid(OrderItem orderItem) {
        Long id = orderItem.getBook().getId();
        Book byIdLocked = bookRepository.findByIdLocked(id).orElseThrow(()->new UsernameNotFoundException("کتاب یافت نشد"));

        byIdLocked.setStock(byIdLocked.getStock() + orderItem.getQuantity());
        bookRepository.save(byIdLocked);


    }
}
