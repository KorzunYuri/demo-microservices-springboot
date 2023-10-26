package com.yurykorzun.demo.microservices.springboot.service.credit.model;

import lombok.Getter;

@Getter
public enum CreditCurrency {

    USD ("USA dollar"), EUR("Euro");

    private String description;

    CreditCurrency(String desc) {
        this.description = desc;
    }

}
