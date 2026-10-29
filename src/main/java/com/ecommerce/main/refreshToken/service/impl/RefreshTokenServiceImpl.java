package com.ecommerce.main.refreshToken.service.impl;

import com.ecommerce.main.auth.service.impl.CustomUserDetails;
import com.ecommerce.main.auth.service.impl.JwtService;
import com.ecommerce.main.common.exception.RefreshTokenNotFound;
import com.ecommerce.main.refreshToken.dto.RefreshTokenResponse;
import com.ecommerce.main.refreshToken.entity.RefreshToken;
import com.ecommerce.main.refreshToken.repository.RefreshTokenRepository;
import com.ecommerce.main.refreshToken.service.RefreshTokenService;
import com.ecommerce.main.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Duration REFRESH_TTL = Duration.ofDays(7);
    private static final String REFRESH_PATH = "/api/v1/refreshToken/verify";
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;


    @Override
    public RefreshTokenResponse verifyRefreshToken(String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(refreshToken).orElseThrow(RefreshTokenNotFound::new);

        if (!ifRefreshTokenExpired(refreshToken1) && !ifRefreshTokenRevoked(refreshToken1)) {
            RefreshToken renewedToken = renewToken(refreshToken1);
            String jwtToken = jwtService.generateToken(new CustomUserDetails(refreshToken1.getUser()));


            return new RefreshTokenResponse(jwtToken, generateRefreshCookie(renewedToken.getToken()));
        } else if (refreshToken1.isRevoked()) {
            refreshTokenRepository.updateByFamilyId(refreshToken1.getFamilyId());
            return new RefreshTokenResponse(null, null);
        }
        return new RefreshTokenResponse(null, null);
    }

    private boolean ifRefreshTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(Instant.now());


    }

    /// renew the old refresh token
    private RefreshToken renewToken(RefreshToken refreshToken) {
        RefreshToken newRefreshToken = RefreshToken.builder().familyId(refreshToken.getFamilyId()).token(UUID.randomUUID().toString()).user(refreshToken.getUser()).expiryDate(Instant.now().plus(7L, ChronoUnit.DAYS)).build();
        refreshTokenRepository.save(newRefreshToken);
        return newRefreshToken;


    }

    private boolean ifRefreshTokenRevoked(RefreshToken refreshToken) {
        return refreshToken.isRevoked();
    }


    /// new refresh token when user log in //
    private RefreshToken createNewRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder().user(user).familyId(UUID.randomUUID()).token(UUID.randomUUID().toString()).expiryDate(Instant.now().plus(7L, ChronoUnit.DAYS)).build();
        return refreshTokenRepository.save(refreshToken);
    }

    private ResponseCookie generateRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(REFRESH_PATH)
                .maxAge(REFRESH_TTL)
                .build();
    }
}
