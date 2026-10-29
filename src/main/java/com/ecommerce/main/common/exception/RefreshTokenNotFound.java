package com.ecommerce.main.common.exception;

public class RefreshTokenNotFound extends RuntimeException {
    public RefreshTokenNotFound() {
        super("رفرش توکن مغتبر نیست");
    }
}
