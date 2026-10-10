package com.ecommerce.main.auth.service.impl;

import com.ecommerce.main.auth.dto.ResetPasswordListener;
import com.ecommerce.main.mail.SmtpEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PasswordResetRequestedEvent {
    private final SmtpEmailService smtpEmailService;

    @Value("${app.mail.base-url}")
    private String baseUrl;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onResetRequest(ResetPasswordListener event) {
        smtpEmailService.sendPasswordResetEmail(event.email(), baseUrl + "/reset-password?token=" + event.token());
    }
}
