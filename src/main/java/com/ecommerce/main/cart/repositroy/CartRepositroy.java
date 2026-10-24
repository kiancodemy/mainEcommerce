package com.ecommerce.main.cart.repositroy;

import com.ecommerce.main.cart.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepositroy extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = {"user","items", "items.book"})
    Optional<Cart> findByUserId(Integer userId);
}