package com.ecommerce.main.common.exception;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
        super("سبد خرید خالی است");
    }
}
