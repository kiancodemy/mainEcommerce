package com.ecommerce.main.refreshToken.controller;

import com.ecommerce.main.auth.dto.AuthResponse;
import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.refreshToken.service.impl.RefreshTokenServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/refreshtoken/")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenServiceImpl refreshTokenService;

    @Operation(summary = "after visiting the site for the first time or when jwt is expired after limited amount of time we send refresh token to get new jwt without logging again . we use this around react app to do it when user open the site or when user get 401 ( authentication) error in order to re-new the jwt token")
    @PostMapping("verify")
    public ResponseEntity<ApiResponse> verifyRefreshToken(@CookieValue(name = "refreshToken") String refreshToken) {
        AuthResponse refreshTokenResponse = refreshTokenService.verifyRefreshToken(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenResponse.refreshToken().toString()).body(new ApiResponse("refresh token is successful", refreshTokenResponse.JwtToken()));
    }
}
