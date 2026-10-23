package com.ecommerce.main.cart.controller;

import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;
import com.ecommerce.main.cart.service.impl.CartServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart/")
@RequiredArgsConstructor
class CartController {
    private final CartServiceImpl cartService;

    @PostMapping("addCard")
    public ResponseEntity<ApiResponse> AddToCard(Authentication authentication,@Valid @RequestBody AddCartItemRequest request) {
        CartResponse cartResponse = cartService.addItem(authentication.getName(), request);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", cartResponse));
    }

    @GetMapping
    public CartResponse getCart(Authentication authentication) {
        return cartService.getCart(authentication.getName());
    }

    @PostMapping("/items")
    public CartResponse add(Authentication authentication,
                            @Valid @RequestBody AddCartItemRequest request) {
        return cartService.addItem(authentication.getName(), request);
    }

    @DeleteMapping("/items/{bookId}")
    public CartResponse remove(Authentication authentication,
                               @PathVariable Long bookId) {
        return cartService.removeItem(authentication.getName(), bookId);
    }

    @DeleteMapping
    public CartResponse clear(Authentication authentication) {
        return cartService.clearCart(authentication.getName());
    }

}
