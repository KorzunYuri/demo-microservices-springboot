package com.yurykorzun.demo.microservices.springboot.commons.exceptions;

public class MessageExposedException extends ManagedException {
    public MessageExposedException(Exception e) {
        super(e);
    }
}
