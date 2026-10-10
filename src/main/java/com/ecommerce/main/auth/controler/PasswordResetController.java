package com.ecommerce.main.auth.controler;

import com.ecommerce.main.auth.dto.ForgotPasswordRequest;
import com.ecommerce.main.auth.dto.ResetPasswordRequest;
import com.ecommerce.main.auth.service.impl.PasswordResetServiceImpl;
import com.ecommerce.main.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class PasswordResetController {
    private final PasswordResetServiceImpl passwordResetService;


    @GetMapping("forgetPassword")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        passwordResetService.forgotPassword(forgotPasswordRequest.email());
        return ResponseEntity.ok().body(new ApiResponse("لینک بازیابی رمز عبور به ایمیل شما ارسال شد", null));

    }


    @GetMapping("resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        passwordResetService.resetPassword(resetPasswordRequest.token(), resetPasswordRequest.newPassword());
        return ResponseEntity.ok().body(new ApiResponse("با موفقیت انجام شد", null));

    }

}
