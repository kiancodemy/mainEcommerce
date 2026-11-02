package com.ecommerce.main.auth.controler;

import com.ecommerce.main.auth.dto.LoginRequest;
import com.ecommerce.main.auth.dto.AuthResponse;
import com.ecommerce.main.auth.dto.SignupRequest;
import com.ecommerce.main.auth.service.impl.AuthServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/auth/")
public class AuthController {
    private final AuthServiceImpl authService;


    @PostMapping("signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse signup = authService.signup(request);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,signup.refreshToken().toString()).body(new ApiResponse("ثبت نام با موفقیت انجام شد", signup.JwtToken()));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse login = authService.login(request);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, login.refreshToken().toString()).body(new ApiResponse("ورود با موفقیت انجام شد", login.JwtToken()));
    }

    @PostMapping("logout")
    public ResponseEntity<ApiResponse> logout(@CookieValue("refreshToken") String refreshToken) {
        log.info("refresh token is "+ refreshToken);
        AuthResponse authResponse = authService.logOut(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authResponse.refreshToken().toString()).body(new ApiResponse("خروج با موفقیت انجام شد", authResponse.JwtToken()));
    }
}
