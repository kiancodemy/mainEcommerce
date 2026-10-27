package com.ecommerce.main.payment.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyRequest(

        @NotBlank(message = "نمیتواند خالی باشد")
        String authority
) {
}
