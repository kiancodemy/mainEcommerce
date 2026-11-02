package com.ecommerce.main.common.exception;

public class PasswordIsIncorrect extends RuntimeException {
    public PasswordIsIncorrect() {
        super("زمز عبور اشتباه است");
    }
}
