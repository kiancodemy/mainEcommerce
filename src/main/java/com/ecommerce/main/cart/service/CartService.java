package com.ecommerce.main.cart.service;

import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;

public interface CartService {
    CartResponse addItem(Integer id, AddCartItemRequest request);
    CartResponse removeItem(Integer id, Long bookId);
    CartResponse clearCart(Integer id);
    CartResponse getCart(Integer idl);

}
