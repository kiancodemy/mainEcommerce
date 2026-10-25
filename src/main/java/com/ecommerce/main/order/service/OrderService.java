package com.ecommerce.main.order.service;

import com.ecommerce.main.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse create(Long userId,Long addressId);

    List<OrderResponse> findAll(Long userId);

    OrderResponse findOne(Long userId, Long id);

    /** Cancels an order that is still waiting for payment. */
    OrderResponse cancel(Long userId, Long id);


}
