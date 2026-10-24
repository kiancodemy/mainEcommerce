package com.ecommerce.main.address.dto;

import com.ecommerce.main.address.entity.Address;

import java.time.Instant;

public record AddressResponse(
        Integer id,
        String province,
        String city,
        String postalCode,
        String phoneNumber,
        String staticNumber,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

}