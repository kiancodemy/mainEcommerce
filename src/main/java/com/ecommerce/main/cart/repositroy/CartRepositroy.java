package com.ecommerce.main.cart.repositroy;

import com.ecommerce.main.cart.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepositroy extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = {"items", "items.book"})
    Optional<Cart> findByUserEmail(String email);
}