package com.ecommerce.main.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "نام دسته‌بندی الزامی است")
        @Size(max = 40, message = "نام دسته‌بندی نمی‌تواند بیشتر از ۴۰ کاراکتر باشد")
        String name
) {
}
