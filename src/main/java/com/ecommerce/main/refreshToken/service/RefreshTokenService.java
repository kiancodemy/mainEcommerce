package com.ecommerce.main.refreshToken.service;

import com.ecommerce.main.refreshToken.dto.RefreshTokenResponse;

public interface RefreshTokenService {
   RefreshTokenResponse verifyRefreshToken(String refreshToken);

}
