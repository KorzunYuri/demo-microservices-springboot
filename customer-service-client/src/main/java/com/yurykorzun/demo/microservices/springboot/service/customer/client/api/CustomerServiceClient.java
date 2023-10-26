package com.yurykorzun.demo.microservices.springboot.service.customer.client.api;

import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerPresenceResponse;
import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerServiceClient {

    Mono<CustomerPresenceResponse> checkCustomerPresence(CustomerDto dto);

}
