package com.ecommerce.main.auth.service;

import com.ecommerce.main.auth.dto.LoginRequest;
import com.ecommerce.main.auth.dto.SignupRequest;
import com.ecommerce.main.common.dto.ApiResponse;

public interface AuthService {

    void signup(SignupRequest request);

    String  login(LoginRequest request);


}
