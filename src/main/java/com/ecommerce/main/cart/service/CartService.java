package com.ecommerce.main.cart.service;

import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;

public interface CartService {
    CartResponse addItem(Long id, AddCartItemRequest request);
    CartResponse removeItem(Long id, Long bookId);
    CartResponse clearCart(Long id);
    CartResponse getCart(Long idl);

}
