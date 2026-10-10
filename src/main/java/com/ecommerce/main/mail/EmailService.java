package com.ecommerce.main.mail;

public interface EmailService {
    void sendVerificationEmail(String to, String verifyLink);
    void sendPasswordResetEmail(String to, String resetLink);
}
