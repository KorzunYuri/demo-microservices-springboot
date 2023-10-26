package com.yurykorzun.demo.microservices.springboot.service.customer.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CustomerPresenceResponse {

    private CustomerDto customer;

    private CustomerPresenceStatus presenceStatus;

    @Builder.Default
    private Date timestamp = new Date();
}
