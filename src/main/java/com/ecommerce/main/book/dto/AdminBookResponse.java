package com.ecommerce.main.book.dto;

import com.ecommerce.main.book.enums.Languages;

import java.math.BigDecimal;

public record AdminBookResponse(Integer id,
                                String title,
                                Boolean status,
                                String description,
                                String publisher,
                                String translator,
                                Languages language,
                                Integer publicationYear,
                                Integer pages,
                                BigDecimal price,
                                BigDecimal costPrice,
                                Integer stock,
                                String coverImageUrl) {
}
