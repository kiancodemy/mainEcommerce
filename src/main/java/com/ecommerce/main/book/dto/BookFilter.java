package com.ecommerce.main.book.dto;

import java.math.BigDecimal;

public record BookFilter(Long categoryId, Long subCategoryId,      // a subcategory
                         BigDecimal minPrice,
                         BigDecimal maxPrice,
                         String search,
                         Boolean inStock,
                         String language) {
}
