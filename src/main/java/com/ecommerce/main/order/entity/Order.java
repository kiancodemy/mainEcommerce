package com.ecommerce.main.order.entity;

import com.ecommerce.main.common.components.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Embedded
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> Items;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    public void addItem(OrderItem orderItem) {
        this.Items.add(orderItem);
        orderItem.setOrder(this);

    }
}
