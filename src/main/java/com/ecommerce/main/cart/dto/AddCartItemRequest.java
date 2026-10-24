package com.ecommerce.main.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest(@NotNull(message = "شناسه کتاب الزامی است") Long  bookId,
                                 @Min(message = "حداقل باید یک کتاب انتخاب کنید", value = 1) int quantity) {
}
