package com.ecommerce.main.order.repository;

import com.ecommerce.main.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.metal.OceanTheme;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order>findByUserIdAndId(Long userId, Long orderId);

}