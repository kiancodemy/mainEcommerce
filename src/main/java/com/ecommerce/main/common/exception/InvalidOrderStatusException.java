package com.ecommerce.main.common.exception;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException() {
        super("فقط سفارش‌های در انتظار پرداخت قابل لغو هستند");
    }
}
