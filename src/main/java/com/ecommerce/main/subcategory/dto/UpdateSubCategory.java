package com.ecommerce.main.subcategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateSubCategory(
        @NotNull(message = "شناسه گروه الزامی است") @Positive(message = "اعداد باید مثیت باشند")  Long categoryId,
        @NotNull(message = "شناسه الزامی است") @Positive(message = "اعداد باید مثیت باشند")  Long id,
        @NotBlank(message = "نام دسته‌بندی الزامی است")
        @Size(max = 40, message = "نام دسته‌بندی نمی‌تواند بیشتر از ۴۰ کاراکتر باشد")
        String name) {
}
