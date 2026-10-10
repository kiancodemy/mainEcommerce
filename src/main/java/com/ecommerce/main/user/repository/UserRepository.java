package com.ecommerce.main.user.repository;

import com.ecommerce.main.user.entity.User;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select s from User s where s.email=?1 ")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}