package com.ecommerce.main.auth.service;

import com.ecommerce.main.user.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    void createUser(String email, String passWord);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    void updatePassword(User user, String updatedPassword);


}
