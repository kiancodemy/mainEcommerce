package com.ecommerce.main.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ShippingAddress {

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
