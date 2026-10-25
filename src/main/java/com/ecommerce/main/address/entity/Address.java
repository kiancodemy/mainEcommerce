package com.ecommerce.main.address.entity;

import com.ecommerce.main.common.components.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;

    @Column(nullable = false)
    private Long userId;


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

}