package com.ecommerce.main.refreshToken.service;

import com.ecommerce.main.auth.dto.AuthResponse;

public interface RefreshTokenService {
   AuthResponse verifyRefreshToken(String refreshToken);

}
