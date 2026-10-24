package com.ecommerce.main.cart.controller;

import com.ecommerce.main.auth.service.impl.CustomUserDetails;
import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;
import com.ecommerce.main.cart.service.impl.CartServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart/")
@RequiredArgsConstructor
class CartController {
    private final CartServiceImpl cartService;

    @PostMapping("addCard")
    public ResponseEntity<ApiResponse> AddToCard(Authentication authentication, @Valid @RequestBody AddCartItemRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        CartResponse cartResponse = cartService.addItem(userDetails.getId(), request);
        return ResponseEntity.ok().body(new ApiResponse("موفقیت امیز بود", cartResponse));
    }

    @GetMapping
    public CartResponse getCart(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return cartService.getCart(userDetails.getId());
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse> add(@Valid @AuthenticationPrincipal(expression = "id") Integer id, @RequestBody AddCartItemRequest request) {
        CartResponse cartResponse = cartService.addItem(id, request);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت اضافه شد", cartResponse));


    }

    @DeleteMapping("/items/{bookId}")
    public ResponseEntity<ApiResponse> remove(@AuthenticationPrincipal(expression = "id") Integer id, @PathVariable Long bookId) {

        CartResponse cartResponse = cartService.removeItem(id, bookId);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت حذف  شد", cartResponse));

    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> clear(@AuthenticationPrincipal(expression = "id") Integer id) {

        CartResponse cartResponse = cartService.clearCart(id);
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت حذف  شد", cartResponse));

    }

}
