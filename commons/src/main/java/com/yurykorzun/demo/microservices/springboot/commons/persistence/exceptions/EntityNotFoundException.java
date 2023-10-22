package com.yurykorzun.demo.microservices.springboot.commons.persistence.exceptions;

import com.yurykorzun.demo.microservices.springboot.commons.exceptions.MessageExposedException;

public class EntityNotFoundException extends MessageExposedException {
    private final String resourceName;
    private final String id;

    public EntityNotFoundException(String resourceName, String id) {
        super();
        this.resourceName = resourceName;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Entity %s with id %s not found", resourceName, id);
    }
}
