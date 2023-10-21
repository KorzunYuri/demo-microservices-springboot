package com.yurykorzun.demo.microservices.springboot.service.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

}
