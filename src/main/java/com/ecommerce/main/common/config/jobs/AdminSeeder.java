package com.ecommerce.main.common.config.jobs;

import com.ecommerce.main.user.dto.Role;
import com.ecommerce.main.user.entity.User;
import com.ecommerce.main.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/// create admin at the start up if no admin available by this email;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationRunner {
    private static final String email = "kiankheirani@gmail.com";
    private static final String password = "12345678";


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean exist = userRepository.existsByEmail(email);
        if (exist) {
            log.info("User with email {} already exists", email);
            return;
        }
        User newUser = User.builder().email(email).password(passwordEncoder.encode(passwordEncoder.encode(password))).role(Role.ADMIN).build();
        userRepository.save(newUser);
        log.info("User with email {} created", email);

    }
}
