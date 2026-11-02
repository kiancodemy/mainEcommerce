package com.ecommerce.main.auth.dto;

import org.springframework.http.ResponseCookie;

public record AuthResponse(String JwtToken,
                           ResponseCookie refreshToken) {
}
