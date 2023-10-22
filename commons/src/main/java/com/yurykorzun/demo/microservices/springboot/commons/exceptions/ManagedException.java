package com.yurykorzun.demo.microservices.springboot.commons.exceptions;

/**
 *  the idea is to add functionality to exception handling
 *  we wrap extension and then process it anywhere else based on wrapper properties or class
 */
public abstract class ManagedException extends RuntimeException {
    ManagedException(){};
}
