package com.yurykorzun.demo.microservices.springboot.service.credit.dto;

import com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CreditApplicationDto {

    private CustomerDto customer;

    private CustomerDto guarantor;

    private String currency;

    private double amount;

    private String periodUnit;

    private long periodLength;

}
