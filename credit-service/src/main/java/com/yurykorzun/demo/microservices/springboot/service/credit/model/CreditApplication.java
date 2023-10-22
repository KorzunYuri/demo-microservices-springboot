package com.yurykorzun.demo.microservices.springboot.service.credit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.TimeUnit;

@Document(collection = "applications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditApplication {

    private String customerId;

    private String guarantorId;

    private Currency currency;

    private double amount;

    private TimeUnit periodUnit;

    private long periodLength;

}
