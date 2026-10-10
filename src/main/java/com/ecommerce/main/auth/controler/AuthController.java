package com.ecommerce.main.auth.controler;

import com.ecommerce.main.auth.dto.LoginRequest;
import com.ecommerce.main.auth.dto.SignupRequest;
import com.ecommerce.main.auth.service.impl.AuthServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/")
public class AuthController {
    private final AuthServiceImpl authService;


    @PostMapping("signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok().body(new ApiResponse("ثبت نام با موفقیت انجام شد", null));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok().body(new ApiResponse("ورود با موفقیت انجام شد", token));
    }
}
