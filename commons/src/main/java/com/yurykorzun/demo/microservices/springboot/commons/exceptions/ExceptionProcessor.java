package com.yurykorzun.demo.microservices.springboot.commons.exceptions;

public interface ExceptionProcessor {

    /**
     *  clarify error message, hide sensitive information, or return input exception without modifying
     */
    Exception process(Exception e);
}
