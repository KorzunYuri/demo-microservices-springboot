package com.yurykorzun.demo.microservices.springboot.service.credit.dto;

import com.yurykorzun.demo.microservices.springboot.service.credit.model.CreditCurrency;
import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor
@ToString
public class CreditApplicationDto {

    private String id;

    private CustomerDto customer;

    private CustomerDto guarantor;

    private CreditCurrency currency;

    private double amount;

    private TimeUnit periodUnit;

    private long periodLength;

}
