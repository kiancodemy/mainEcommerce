package com.ecommerce.main.book.dto;

import java.math.BigDecimal;

public record BookResponse(String title,
                           BigDecimal price,
                           BigDecimal costPrice,
                           String coverImageUrl) {
}
