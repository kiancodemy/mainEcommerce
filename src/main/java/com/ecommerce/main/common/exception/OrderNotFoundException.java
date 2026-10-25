package com.ecommerce.main.common.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("سفارش مورد نظر یافت نشد");
    }
}
