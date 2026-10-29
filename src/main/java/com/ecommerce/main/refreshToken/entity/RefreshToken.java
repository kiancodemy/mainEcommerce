package com.ecommerce.main.refreshToken.entity;

import com.ecommerce.main.common.components.BaseEntity;
import com.ecommerce.main.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id",name="user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private UUID familyId;


    @Column(nullable = false)
    @Builder.Default
    private boolean isRevoked=false;

    @Column(nullable = false)
    private Instant expiryDate;
}