package com.ecommerce.main.mail;

import com.ecommerce.main.common.components.EmailTemplateLoader;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class SmtpEmailService implements EmailService {

    private final EmailTemplateLoader emailTemplateLoader;
    private final JavaMailSender mailSender;


    @Value("${app.mail.from}")
    private String from;

    @Override
    @Async
    public void sendVerificationEmail(String to, String verifyLink) {
        String html = render("verification-email.html", Map.of(
                "verifyLink", verifyLink,
                "userName", ""   // optionally pass the user's name
        ));
        send(to, "تأیید ایمیل حساب کاربری", html);

    }

    @Override
    @Async
    public void sendPasswordResetEmail(String to, String resetLink) {
        String html = render("password-reset-email.html", Map.of(
                "resetLink", resetLink
        ));
        send(to, "بازیابی رمز عبور", html);


    }

    private void send(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}", to, e);
        }
    }

    private String render(String templateName, Map<String, String> variables) {
        String html = emailTemplateLoader.load(templateName);
        for (var entry : variables.entrySet()) {
            html = html.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return html;
    }

}


