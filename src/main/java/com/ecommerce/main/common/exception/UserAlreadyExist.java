package com.ecommerce.main.common.exception;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist() {
        super("این نام کاربری قبلا انتخاب شده لطفا نام کاربری دیگری انتخاب کنید");
    }
}
