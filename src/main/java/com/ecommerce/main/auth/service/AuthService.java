package com.ecommerce.main.auth.service;

import com.ecommerce.main.auth.dto.LoginRequest;
import com.ecommerce.main.auth.dto.AuthResponse;
import com.ecommerce.main.auth.dto.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
    AuthResponse logOut(String cookie);


}
