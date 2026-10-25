package com.ecommerce.main.order.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        String bookTitle,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {
}
