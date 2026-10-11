package com.ecommerce.main.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "توکن را وارد کنید") String token,
        @NotBlank(message = "رمز عبور جدید را وارد کنید") @Size(message = "رمز عبور حد اقل 8 کاراکتر باشد", min = 8) String newPassword) {
}