package com.ecommerce.main.order.repository;

import com.ecommerce.main.order.entity.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.plaf.metal.OceanTheme;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"Items"})
    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"Items"})
    Optional<Order> findByUserIdAndId(Long userId, Long orderId);

    Optional<Order> findByAuthorityCode(String authorityCode);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o  from Order o where o.id=?1")
    Optional<Order> findByIdLocked(Long id);



    @Query("SELECT o.id FROM Order o WHERE o.expiredAt < :now")
    List<Long> findExpiredOrder(@Param("now") Instant now);

}