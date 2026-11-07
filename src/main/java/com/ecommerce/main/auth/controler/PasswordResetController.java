package com.ecommerce.main.auth.controler;

import com.ecommerce.main.auth.dto.AuthResponse;
import com.ecommerce.main.auth.dto.ForgotPasswordRequest;
import com.ecommerce.main.auth.dto.ResetPasswordRequest;
import com.ecommerce.main.auth.service.impl.PasswordResetServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class PasswordResetController {
    private final PasswordResetServiceImpl passwordResetService;


    @PostMapping("forgetPassword")
    @Operation(summary = "forget the password ")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        passwordResetService.forgotPassword(forgotPasswordRequest.email());
        return ResponseEntity.ok().body(new ApiResponse("لینک بازیابی رمز عبور به ایمیل شما ارسال شد", null));

    }


    @PostMapping("resetPassword")
    @Operation(summary = "reset the password after receiving email that sends the code ")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        AuthResponse authResponse = passwordResetService.resetPassword(resetPasswordRequest.token(), resetPasswordRequest.newPassword());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,authResponse.refreshToken().toString()).body(new ApiResponse("با موفقیت انجام شد", authResponse.JwtToken()));

    }

}
