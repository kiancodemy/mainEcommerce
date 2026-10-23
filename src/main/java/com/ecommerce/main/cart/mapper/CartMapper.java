package com.ecommerce.main.cart.mapper;

import com.ecommerce.main.cart.dto.CartItemResponse;
import com.ecommerce.main.cart.dto.CartResponse;
import com.ecommerce.main.cart.entity.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartResponse toCartResponse(Cart card) {
        int totalQuantity = card.getItems().stream().map(b -> b.getQuantity()).reduce(0, (a, b) -> a + b);
        BigDecimal totalPrice = card.getItems().stream().map(b -> b.getBook().getPrice().multiply(BigDecimal.valueOf(b.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<CartItemResponse> cartItemResponseList = card.getItems().stream().map(c -> new CartItemResponse(c.getBook().getId(), c.getBook().getTitle(), c.getBook().getCoverImageUrl(), c.getBook().getPrice(), c.getQuantity())).toList();
        return new CartResponse(cartItemResponseList, totalQuantity, totalPrice);


    }
    public CartResponse empty() {
        return new CartResponse(List.of(), 0, BigDecimal.ZERO);
    }

}
