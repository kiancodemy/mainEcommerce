package com.ecommerce.main.order.mapper;

import com.ecommerce.main.address.entity.Address;
import com.ecommerce.main.cartItems.CartItem;
import com.ecommerce.main.order.dto.OrderItemResponse;
import com.ecommerce.main.order.dto.OrderResponse;

import com.ecommerce.main.order.dto.ShippingAddressResponse;
import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.order.entity.OrderItem;
import com.ecommerce.main.order.entity.ShippingAddress;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getTotalPrice(),
                toShippingAddressResponse(order.getShippingAddress()),
                order.getItems().stream().map(this::toItemResponse).toList(),
                order.getCreatedAt(),
                order.getUpdatedAt());
    }

    public List<OrderResponse> toResponses(List<Order> orders) {
        return orders.stream().map(this::toResponse).toList();
    }

    public OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(// does not initialize the lazy Book proxy
                item.getTitle(),
                item.getPrice(),
                item.getQuantity(),
                item.totalPrice());
    }

    public ShippingAddressResponse toShippingAddressResponse(ShippingAddress a) {
        return new ShippingAddressResponse(
                a.getProvince(), a.getCity(), a.getPostalCode(),
                a.getPhoneNumber(), a.getStaticNumber(), a.getDescription());
    }

    // Copies the address so later changes to it never affect this order.
    public ShippingAddress toShippingAddress(Address address) {
        return ShippingAddress.builder()
                .province(address.getProvince())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .phoneNumber(address.getPhoneNumber())
                .staticNumber(address.getStaticNumber())
                .description(address.getDescription())
                .build();
    }

    // Copies title and price so later changes to the book never affect this order.
    public OrderItem toOrderItem(CartItem cartItem) {
        return OrderItem.builder()
                .book(cartItem.getBook())
                .title(cartItem.getBook().getTitle())
                .price(cartItem.getBook().getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
