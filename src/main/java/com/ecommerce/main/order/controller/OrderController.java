package com.ecommerce.main.order.controller;

import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.order.dto.OrderResponse;
import com.ecommerce.main.order.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/address")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")

public class OrderController {
    private final OrderServiceImpl orderService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long id) {
        OrderResponse order = orderService.findOne(userId, id);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", order));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> geAllll(@AuthenticationPrincipal(expression = "id") Long userId) {
        List<OrderResponse> all = orderService.findAll(userId);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", all));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> cancelOrder(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long id) {
        OrderResponse order = orderService.cancel(userId, id);

        return ResponseEntity.ok().body(new ApiResponse("سقارش کنسل شد", order));
    }

}
