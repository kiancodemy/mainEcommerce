package com.ecommerce.main.auth.service.impl;

import com.ecommerce.main.auth.dto.LoginRequest;
import com.ecommerce.main.auth.dto.AuthResponse;
import com.ecommerce.main.auth.dto.SignupRequest;
import com.ecommerce.main.auth.service.AuthService;
import com.ecommerce.main.common.exception.PasswordIsIncorrect;
import com.ecommerce.main.refreshToken.service.impl.RefreshTokenServiceImpl;
import com.ecommerce.main.security.CustomUserDetailsService;
import com.ecommerce.main.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final JwtService jwtService;

    @Override
    public AuthResponse signup(SignupRequest request) {
        User newUser = userService.createUser(request.email(), request.password());
        CustomUserDetails customUserDetails = new CustomUserDetails(newUser);
        return generateRefreshTokenCookieAndJwtToken(customUserDetails);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        boolean isPasswordCorrect = passwordEncoder.matches(request.password(), userDetails.getPassword());
        if (!isPasswordCorrect) {

            throw new PasswordIsIncorrect();
        }
        return generateRefreshTokenCookieAndJwtToken(userDetails);
    }

    @Override
    public AuthResponse logOut(String cookie) {
        ResponseCookie responseCookie = refreshTokenService.clearRefreshTokenCookie(cookie);
        return new AuthResponse(null, responseCookie);
    }

    ///generate jwt token and refreshToken cookie
    private AuthResponse generateRefreshTokenCookieAndJwtToken(CustomUserDetails userDetails) {
        String authToken = jwtService.generateToken(userDetails);

        String refreshToken = refreshTokenService.generateNewRefreshToken(userDetails);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshTokenCookie(refreshToken);
        return new AuthResponse(authToken, responseCookie);
    }
}
