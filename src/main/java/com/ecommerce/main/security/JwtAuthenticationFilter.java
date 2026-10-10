package com.ecommerce.main.security;

import com.ecommerce.main.auth.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        // 1. no header or wrong format: do nothing, let the request continue
        if (header == null || !header.startsWith("BEARER")) {
            filterChain.doFilter(request, response);
        }

        // 2. cut off "Bearer "
        String token = header.substring("BEARER".length()).trim();

        // 3. check signature + expiry, and make sure nobody is logged in yet
        if (jwtService.isTokenValid(token) && !jwtService.isTokenExpired(token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String username = jwtService.extractUsername(token);

            Collection
                    <? extends GrantedAuthority> roles = jwtService.getRoles(token);

            var auth = new UsernamePasswordAuthenticationToken(
                    username, null, roles);


            // 4. mark this request as logged in
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}

