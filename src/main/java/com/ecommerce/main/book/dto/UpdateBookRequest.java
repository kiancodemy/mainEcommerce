package com.ecommerce.main.book.dto;

import com.ecommerce.main.book.enums.Languages;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateBookRequest(
        @NotNull(message = "شناسه کتاب الزامی است")
        Integer id,

        @NotBlank(message = "عنوان کتاب الزامی است")
        @Size(max = 255, message = "عنوان کتاب نمی‌تواند بیشتر از ۲۵۵ کاراکتر باشد")
        String title,

        @Size(max = 400, message = "توضیحات نمی‌تواند بیشتر از ۴۰۰ کاراکتر باشد")
        String description,

        @Size(max = 200, message = "نام ناشر نمی‌تواند بیشتر از ۲۰۰ کاراکتر باشد")
        String publisher,

        @NotNull(message = "فعال بودن یا نبودن الزامی است")
        Boolean status,

        @Size(max = 40, message = "نام مترجم نمی‌تواند بیشتر از ۴۰ کاراکتر باشد")
        String translator,

        @NotNull(message = "زبان کتاب الزامی است")
        Languages language,

        @Min(value = 1000, message = "سال انتشار باید حداقل ۱۰۰۰ باشد")
        @Max(value = 2100, message = "سال انتشار باید حداکثر ۲۱۰۰ باشد")
        Integer publicationYear,

        @Min(value = 1, message = "تعداد صفحات باید حداقل ۱ باشد") @NotNull(message = "تعداد صفحات کتاب الزامی است")
        Integer pages,

        @NotNull(message = "قیمت کتاب الزامی است")
        @DecimalMin(value = "0.0", inclusive = false, message = "قیمت کتاب باید بیشتر از صفر باشد")
        @Digits(integer = 12, fraction = 0, message = "قیمت تمام‌شده باید حداکثر ۱۲ رقم صحیح و بدون اعشار باشد")
        BigDecimal price,


        @NotNull(message = "قیمت تمام‌شده کتاب الزامی است")
        @DecimalMin(value = "0.0", inclusive = false, message = "قیمت تمام‌شده باید بیشتر از صفر باشد")
        @Digits(integer = 12, fraction = 0, message = "قیمت تمام‌شده باید حداکثر ۱۲ رقم صحیح و بدون اعشار باشد")
        BigDecimal costPrice,

        @NotNull(message = "موجودی کتاب الزامی است")
        @Min(value = 0, message = "موجودی کتاب نمی‌تواند منفی باشد")
        Integer stock,


        @NotNull(message = "انتخاب دسته‌بندی الزامی است")
        Integer categoryId,

        @NotNull(message = "انتخاب زیردسته الزامی است")
        Integer subcategoryId


) {
}
