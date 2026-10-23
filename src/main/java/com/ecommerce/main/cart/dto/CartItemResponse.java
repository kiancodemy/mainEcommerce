package com.ecommerce.main.cart.dto;

import java.math.BigDecimal;

public record CartItemResponse(Integer bookId, String title, String coverImageUrl,
                               BigDecimal unitPrice, int quantity) {
}
