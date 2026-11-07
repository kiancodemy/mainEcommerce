package com.ecommerce.main.order.controller;

import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.order.dto.OrderResponse;
import com.ecommerce.main.order.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER')")

public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("/{addressId}")
    @Operation(summary = "create new order")
    public ResponseEntity<ApiResponse> creatNewOrder(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long addressId) {
        OrderResponse orderResponse = orderService.create(userId, addressId);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", orderResponse));
    }


    @GetMapping("/{id}")
    @Operation(summary = "get order by its id")
    public ResponseEntity<ApiResponse> getOrderById(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long id) {
        OrderResponse order = orderService.findOne(userId, id);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", order));
    }

    @GetMapping
    @Operation(summary = "get  all orders of the user")
    public ResponseEntity<ApiResponse> geAll(@AuthenticationPrincipal(expression = "id") Long userId) {
        List<OrderResponse> all = orderService.findAll(userId);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", all));
    }

    @PutMapping("/{id}")
    @Operation(summary = "cancel order")
    public ResponseEntity<ApiResponse> cancelOrder(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long id) {
        OrderResponse order = orderService.cancel(userId, id);

        return ResponseEntity.ok().body(new ApiResponse("سقارش کنسل شد", order));
    }

}
