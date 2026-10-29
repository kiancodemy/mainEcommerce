package com.ecommerce.main.refreshToken.controller;

import com.ecommerce.main.common.dto.ApiResponse;
import com.ecommerce.main.refreshToken.dto.RefreshTokenResponse;
import com.ecommerce.main.refreshToken.service.impl.RefreshTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/refreshtoken/")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenServiceImpl refreshTokenService;

    @PostMapping("verify")
    public ResponseEntity<ApiResponse> verifyRefreshToken(@CookieValue(name = "refreshToken") String refreshToken) {
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.verifyRefreshToken(refreshToken);
        ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenResponse.toString()).body(new ApiResponse("find", refreshTokenResponse.JwtToken()));


    }
}
