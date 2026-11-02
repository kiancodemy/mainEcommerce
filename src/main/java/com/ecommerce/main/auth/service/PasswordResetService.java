package com.ecommerce.main.auth.service;

import com.ecommerce.main.auth.dto.AuthResponse;

public interface PasswordResetService {

    void forgotPassword(String email);


    AuthResponse resetPassword(String token, String newPassword);


}
