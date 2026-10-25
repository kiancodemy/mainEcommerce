package com.ecommerce.main.order.dto;

public record ShippingAddressResponse(
        String province,
        String city,
        String postalCode,
        String phoneNumber,
        String staticNumber,
        String description
) {
}
