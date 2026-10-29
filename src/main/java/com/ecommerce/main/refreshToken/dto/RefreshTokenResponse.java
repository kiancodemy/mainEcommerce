package com.ecommerce.main.refreshToken.dto;

import org.springframework.http.ResponseCookie;

public record RefreshTokenResponse(
        String JwtToken,
        ResponseCookie refreshToken

) {
}
