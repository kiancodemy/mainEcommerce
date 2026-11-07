package com.ecommerce.main.cart.controller;

import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;
import com.ecommerce.main.cart.service.impl.CartServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
@Tag(name="cart controller")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequiredArgsConstructor
class CartController {
    private final CartServiceImpl cartService;


    @GetMapping
    @Operation(summary = "find user cart ")
    public ResponseEntity<ApiResponse> getCart(@AuthenticationPrincipal(expression = "id") Long id) {

        CartResponse cart = cartService.getCart(id);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت بازیابی شد", cart));
    }

    @Operation(summary = "add items to cart")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @AuthenticationPrincipal(expression = "id") Long id, @RequestBody AddCartItemRequest request) {
        CartResponse cartResponse = cartService.addItem(id, request);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت اضافه شد", cartResponse));


    }

    @Operation(summary = "remove items from cart")
    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse> remove(@AuthenticationPrincipal(expression = "id") Long id, @PathVariable Long bookId) {

        CartResponse cartResponse = cartService.removeItem(id, bookId);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت حذف  شد", cartResponse));

    }

    @Operation(summary = "clear the cart of order creation")
    @DeleteMapping
    public ResponseEntity<ApiResponse> clear(@AuthenticationPrincipal(expression = "id") Long id) {

        CartResponse cartResponse = cartService.clearCart(id);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت حذف  شد", cartResponse));

    }

}
