package com.ecommerce.main.common.config;

import com.ecommerce.main.order.entity.Order;
import com.ecommerce.main.order.entity.OrderStatus;
import com.ecommerce.main.order.repository.OrderRepository;
import com.ecommerce.main.order.service.impl.OrderServiceImpl;
import com.ecommerce.main.refreshToken.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
@EnableScheduling
@Slf4j
public class Scheduling {
    private final RefreshTokenRepository refreshTokenRepository;
    private final OrderRepository orderRepository;
    private final OrderServiceImpl orderService;

    /// delete all revoked token in order to clean the data base
    @Scheduled(cron = "0 0 /2 * * *")
    public void deleteUsedRefreshToken() {
        refreshTokenRepository.deleteAllRevoked();
    }

    ///  cancel orders if not paid ////
    @Scheduled(cron = "0 0 /2 * * *")
    public void cancelOrdersIfNotPaid() {
        List<Long> expiredOrder = orderRepository.findExpiredOrder(Instant.now());
        for (Long orderId : expiredOrder) {
            Optional<Order> byIdLocked = orderRepository.findByIdLocked(orderId);
            if (byIdLocked.isPresent()) {
                if (byIdLocked.get().getExpiredAt().isBefore(Instant.now()) && byIdLocked.get().getStatus() == OrderStatus.PENDING_PAYMENT) {
                    byIdLocked.get().setStatus(OrderStatus.CANCELLED);
                }
                log.info("Order has been cancelled by user: {}", byIdLocked.get().getId());
            }

        }

    }
}
