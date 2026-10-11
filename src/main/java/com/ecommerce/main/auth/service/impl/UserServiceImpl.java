package com.ecommerce.main.auth.service.impl;

import com.ecommerce.main.auth.service.UserService;
import com.ecommerce.main.user.entity.User;
import com.ecommerce.main.user.enums.Role;
import com.ecommerce.main.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(String email, String passWord) {
        if (existsByEmail(email)) {
            throw new RuntimeException("ایمیل قبلا استفاده شده");
        }
        String encodedPassword = passwordEncoder.encode(passWord);
        User newUser = User.builder().email(email).password(encodedPassword).role(Role.USER).build();
        userRepository.save(newUser);
    }

    @Override
    public void updatePassword(User user, String updatedPassword) {
        user.setPassword(passwordEncoder.encode(updatedPassword));
        userRepository.save(user);


    }


    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("ایمیل معتبر نیست"));
        return user;


    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
