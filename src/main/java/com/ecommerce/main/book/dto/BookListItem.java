package com.ecommerce.main.book.dto;

import java.math.BigDecimal;

public record BookListItem(String title,

                           String coverImageUrl,

                           BigDecimal price,

                           boolean inStock) {
}
