package com.ecommerce.main.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record LoginRequest(

        @NotBlank(message = "ایمیل را وارد کنید")
        @Email(message = "فرمت ایمیل صحیح نیست")
        String email,

        @NotBlank(message = "رمز عبور را وارد کنید")
        @Size(min = 8, message = "رمز عبور باید حداقل ۸ کاراکتر باشد")
        String password
) {
}