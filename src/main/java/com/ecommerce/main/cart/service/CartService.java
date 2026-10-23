package com.ecommerce.main.cart.service;

import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;

public interface CartService {
    CartResponse addItem(String email, AddCartItemRequest request);
    CartResponse removeItem(String email, Long bookId);
    CartResponse clearCart(String email);
    CartResponse getCart(String email);

}
