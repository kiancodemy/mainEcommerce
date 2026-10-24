package com.ecommerce.main.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequest(

        @NotBlank(message = "استان نمی‌تواند خالی باشد")
        @Size(max = 100, message = "استان نباید بیش از ۱۰۰ کاراکتر باشد")
        String province,

        @NotBlank(message = "شهر نمی‌تواند خالی باشد")
        @Size(max = 100, message = "شهر نباید بیش از ۱۰۰ کاراکتر باشد")
        String city,

        @NotBlank(message = "کد پستی نمی‌تواند خالی باشد")
        @Pattern(regexp = "^\\d{10}$", message = "کد پستی باید ۱۰ رقم باشد")
        String postalCode,

        @NotBlank(message = "شماره موبایل نمی‌تواند خالی باشد")
        @Pattern(regexp = "^09\\d{9}$", message = "شماره موبایل معتبر نیست (مثال: 09123456789)")
        String phoneNumber,

        @NotBlank(message = "شماره تلفن ثابت نمی‌تواند خالی باشد")
        @Pattern(regexp = "^0\\d{10}$", message = "شماره تلفن ثابت باید همراه با کد شهر باشد (مثال: 02112345678)")
        String staticNumber,

        // optional
        @Size(max = 500, message = "توضیحات نباید بیش از ۵۰۰ کاراکتر باشد")
        String description
) {
}
