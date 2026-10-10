package com.ecommerce.main.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(

        @NotBlank(message = "ایمیل نمی‌تواند خالی باشد")
        @Email(message = "فرمت ایمیل وارد شده صحیح نیست")
        String email

) {}
