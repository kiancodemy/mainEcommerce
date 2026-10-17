package com.ecommerce.main.auth.service.impl;

import com.ecommerce.main.auth.dto.ResetPasswordListener;
import com.ecommerce.main.auth.entity.PasswordResetToken;
import com.ecommerce.main.auth.repository.PasswordResetTokenRepositoy;
import com.ecommerce.main.auth.service.PasswordResetService;
import com.ecommerce.main.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserServiceImpl userService;
    private final PasswordResetTokenRepositoy passwordResetTokenRepositoy;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    @Transactional
    public void forgotPassword(String email) {

        User user = userService.findByEmail(email);

        // generate random token number
        int randomTokenNumber = RANDOM.nextInt(1_000_000);

        PasswordResetToken entity = PasswordResetToken.builder()
                .token(String.format("%06d",randomTokenNumber))
                .user(user)
                .expiresAt(Instant.now().plus(4, ChronoUnit.MINUTES))
                .build();
        passwordResetTokenRepositoy.save(entity);
        applicationEventPublisher.publishEvent(new ResetPasswordListener(user.getEmail(), entity.getToken()));
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetPassToken = passwordResetTokenRepositoy.findByToken(token).orElseThrow(() -> new RuntimeException("توکن معتبر نیست"));
        if (resetPassToken.isUsed() || resetPassToken.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("توکن منقضی شده");
        }
        userService.updatePassword(resetPassToken.getUser(), newPassword);
        resetPassToken.setUsed(true);

    }
}
