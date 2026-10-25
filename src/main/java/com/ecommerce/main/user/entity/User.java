package com.ecommerce.main.user.entity;

import com.ecommerce.main.common.components.BaseEntity;
import com.ecommerce.main.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Builder
@Setter
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
