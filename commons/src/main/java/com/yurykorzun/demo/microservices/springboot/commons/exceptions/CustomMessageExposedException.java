package com.yurykorzun.demo.microservices.springboot.commons.exceptions;

public class CustomMessageExposedException extends MessageExposedException {

    private final String message;

    public CustomMessageExposedException(Exception e, String message) {
        super(e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
