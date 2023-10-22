package com.yurykorzun.demo.microservices.springboot.service.credit.model;

import lombok.Getter;

@Getter
public enum Currency {

    USD ("USA dollar"), EUR("Euro");

    private String description;

    Currency(String desc) {
        this.description = desc;
    }

}
