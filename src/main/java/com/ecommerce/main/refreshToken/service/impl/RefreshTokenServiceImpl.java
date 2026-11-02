package com.ecommerce.main.refreshToken.service.impl;

import com.ecommerce.main.auth.dto.AuthResponse;
import com.ecommerce.main.auth.service.impl.CustomUserDetails;
import com.ecommerce.main.auth.service.impl.JwtService;
import com.ecommerce.main.common.exception.RefreshTokenNotFound;
import com.ecommerce.main.refreshToken.dto.RefreshTokenResponse;
import com.ecommerce.main.refreshToken.entity.RefreshToken;
import com.ecommerce.main.refreshToken.repository.RefreshTokenRepository;
import com.ecommerce.main.refreshToken.service.RefreshTokenService;
import com.ecommerce.main.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Duration REFRESH_TTL = Duration.ofDays(7);
    private static final String REFRESH_PATH = "/api/v1/auth";
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;


    @Override
    @Transactional
    public AuthResponse verifyRefreshToken(String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(refreshToken).orElseThrow(RefreshTokenNotFound::new);

        if (!ifRefreshTokenExpired(refreshToken1) && !ifRefreshTokenRevoked(refreshToken1)) {
            String renewedToken = renewToken(refreshToken1);
            String jwtToken = jwtService.generateToken(new CustomUserDetails(refreshToken1.getUser()));

            return new AuthResponse(jwtToken, generateRefreshTokenCookie(renewedToken));
        } else if (refreshToken1.isRevoked()) {
            log.warn("some one has already used this token !!!!!");
            refreshTokenRepository.updateByFamilyId(refreshToken1.getFamilyId());
            return new  AuthResponse(null, null);
        }
        return new  AuthResponse(null, null);
    }

    private boolean ifRefreshTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }

    /// revoke ( set revoke to true) the old refresh token and make new refresh token ////
    private String renewToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        RefreshToken newRefreshToken = RefreshToken.builder().familyId(refreshToken.getFamilyId()).token(UUID.randomUUID().toString()).user(refreshToken.getUser()).expiryDate(Instant.now().plus(7L, ChronoUnit.DAYS)).build();
        refreshTokenRepository.save(newRefreshToken);
        return newRefreshToken.getToken();
    }

    private boolean ifRefreshTokenRevoked(RefreshToken refreshToken) {
        return refreshToken.isRevoked();
    }


    /// generate refreshTokenCookie to send to client;
    public ResponseCookie generateRefreshTokenCookie(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path(REFRESH_PATH)
                .maxAge(REFRESH_TTL)
                .build();
    }
    /// clear cookie  for logout
    public ResponseCookie clearRefreshTokenCookie(String cookie) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(cookie).orElseThrow(RefreshTokenNotFound::new);
        refreshTokenRepository.delete(refreshToken);
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(REFRESH_PATH)
                .maxAge(Duration.ZERO)
                .build();
    }
    /// generate new refresh token in database after login or signup
    public String generateNewRefreshToken(CustomUserDetails customUserDetails) {
        UUID familyId = UUID.randomUUID();
        String token=UUID.randomUUID().toString();
        RefreshToken refreshToken=RefreshToken.builder().token(token).familyId(familyId).expiryDate(Instant.now().plus(7L,ChronoUnit.DAYS)).build();
        refreshToken.setUser(customUserDetails.getUser());
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();

    }

    /// deleting all user refresh token when he  change his password--> so every one  will log out of his account by clearing all tokens and they should login with new password;
    public void clearAllRefreshTokens(Long userId) {
        refreshTokenRepository.clearAllRefreshToken(userId);

    }
}
