package com.ecommerce.main.address.entity;

import com.ecommerce.main.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.Instant;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;

    @Column(nullable = false)
    private Integer userId;


    @Column(nullable = false, length = 100)
    private String province;

    @Column(nullable = false, length = 100)
    private String city;

    @Column( nullable = false, length = 10)
    private String postalCode;

    @Column( nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false, length = 11)
    private String staticNumber;

    @Column(length = 500)
    private String description;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}