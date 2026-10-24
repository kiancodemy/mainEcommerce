package com.ecommerce.main.common.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
        super("آدرس مورد نظر یافت نشد");
    }
}
