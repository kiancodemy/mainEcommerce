package com.ecommerce.main.book.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateBookRequest(

        @NotBlank(message = "عنوان کتاب الزامی است")
        @Size(max = 255, message = "عنوان کتاب نمی‌تواند بیشتر از ۲۵۵ کاراکتر باشد")
        String title,

        @Size(max = 4000, message = "توضیحات نمی‌تواند بیشتر از ۴۰۰۰ کاراکتر باشد")
        String description,

        @Size(max = 200, message = "نام ناشر نمی‌تواند بیشتر از ۲۰۰ کاراکتر باشد")
        String publisher,

        @Size(max = 200, message = "نام مترجم نمی‌تواند بیشتر از ۲۰۰ کاراکتر باشد")
        String translator,

        @NotBlank(message = "زبان کتاب الزامی است")
        @Size(max = 10, message = "زبان کتاب نمی‌تواند بیشتر از ۱۰ کاراکتر باشد")
        String language,

        @Min(value = 1000, message = "سال انتشار باید حداقل ۱۰۰۰ باشد")
        @Max(value = 2100, message = "سال انتشار باید حداکثر ۲۱۰۰ باشد")
        Integer publicationYear,

        @Min(value = 1, message = "تعداد صفحات باید حداقل ۱ باشد")
        Integer pages,

        @NotNull(message = "قیمت کتاب الزامی است")
        @DecimalMin(value = "0.0", inclusive = false, message = "قیمت کتاب باید بیشتر از صفر باشد")
        @Digits(integer = 10, fraction = 2, message = "قیمت کتاب باید حداکثر ۱۰ رقم صحیح و ۲ رقم اعشار داشته باشد")
        BigDecimal price,

        @NotNull(message = "قیمت تمام‌شده کتاب الزامی است")
        @DecimalMin(value = "0.0", inclusive = false, message = "قیمت تمام‌شده باید بیشتر از صفر باشد")
        @Digits(integer = 10, fraction = 2, message = "قیمت تمام‌شده باید حداکثر ۱۰ رقم صحیح و ۲ رقم اعشار داشته باشد")
        BigDecimal costPrice,

        @NotNull(message = "موجودی کتاب الزامی است")
        @Min(value = 0, message = "موجودی کتاب نمی‌تواند منفی باشد")
        Integer stock,

        @Size(max = 500, message = "آدرس تصویر جلد نمی‌تواند بیشتر از ۵۰۰ کاراکتر باشد")
        String coverImageUrl,

        @NotNull(message = "انتخاب دسته‌بندی الزامی است")
        Integer categoryId,

        @NotNull(message = "انتخاب زیردسته الزامی است")
        Integer subcategoryId
) {
}