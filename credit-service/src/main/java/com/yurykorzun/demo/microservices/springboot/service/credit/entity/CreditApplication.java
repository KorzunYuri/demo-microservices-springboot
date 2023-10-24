package com.yurykorzun.demo.microservices.springboot.service.credit.entity;

import com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationRegistrationStatus;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditCurrency;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationDeclineReason;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.TimeUnit;

@Document(collection = "applications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreditApplication {

    @Id
    private String id;

    private CustomerDto customer;

    private CustomerDto guarantor;

    private CreditCurrency currency;

    private double amount;

    private TimeUnit periodUnit;

    private long periodLength;

    @Setter
    @Builder.Default
    private CreditApplicationRegistrationStatus status = CreditApplicationRegistrationStatus.NEW;

    @Setter
    private CreditApplicationDeclineReason declineReason;

    @Setter
    private String declineMessage;

}
