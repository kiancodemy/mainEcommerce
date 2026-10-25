package com.ecommerce.main.order.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull(message = "آدرس تحویل باید انتخاب شود")
        Long addressId
) {
}
