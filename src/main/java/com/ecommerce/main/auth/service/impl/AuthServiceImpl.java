package com.ecommerce.main.auth.service.impl;

import com.ecommerce.main.auth.dto.LoginRequest;
import com.ecommerce.main.auth.dto.SignupRequest;
import com.ecommerce.main.auth.service.AuthService;
import com.ecommerce.main.security.CustomUserDetailsService;
import com.ecommerce.main.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void signup(SignupRequest request) {
        userService.createUser(request.email(), request.password());
    }

    @Override
    @Transactional
    public String login(LoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());


        boolean isPasswordCorrect = passwordEncoder.matches(request.password(), userDetails.getPassword());
        if (!isPasswordCorrect) {

            throw new RuntimeException("رمز عبور صحیح نیست");
        }
        String authToken = jwtService.generateToken(userDetails);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        return authToken;
    }
}
