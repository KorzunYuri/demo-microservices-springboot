package com.yurykorzun.demo.microservices.springboot.commons.model.credit;

import lombok.Getter;

@Getter
public enum CreditCurrency {

    USD ("USA dollar"), EUR("Euro");

    private String description;

    CreditCurrency(String desc) {
        this.description = desc;
    }

}
