package com.ecommerce.main.payment.dto;

public record PaymentResult(  boolean success,
                              int refId,
                              String message) {
}
