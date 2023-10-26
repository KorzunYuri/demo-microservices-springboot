package com.yurykorzun.demo.microservices.springboot.service.customer.client.api;

import com.yurykorzun.demo.microservices.springboot.commons.persistence.BaseEntityFields;
import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerFields;
import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerPresenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultCustomerServiceClient implements CustomerServiceClient {

    private final WebClient.Builder customerServiceClientWebClientBuilder;

    @Override
    public Mono<CustomerPresenceResponse> checkCustomerPresence(CustomerDto dto) {
        MultiValueMap<String, String> params = getCustomerPresenceRequestParams(dto);
        return customerServiceClientWebClientBuilder.build().get()
                .uri(   //  TODO inject service uri into library is a non-trivial task
                        "http://customer-service/api/v1/customer/presence",
                        uriBuilder -> uriBuilder.queryParams(params).build())
                .retrieve()
                .bodyToMono(CustomerPresenceResponse.class);
    }

    /**
     *  make a map of request parameters to be used in a GET call to confirm customer presence in the customer-service
     *  @param dto - Customer provided by a client call
     *  @return MultiValueMap with parameters
     */
    private MultiValueMap<String, String> getCustomerPresenceRequestParams(CustomerDto dto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (dto.getId()         != null) params.put(BaseEntityFields.ID,        List.of(dto.getId()));
        if (dto.getPassportId() != null) params.put(CustomerFields.PASSPORT_ID, List.of(dto.getPassportId()));
        if (dto.getFirstName()  != null) params.put(CustomerFields.FIRST_NAME,  List.of(dto.getFirstName()));
        if (dto.getLastName()   != null) params.put(CustomerFields.LAST_NAME,   List.of(dto.getLastName()));
        return params;
    }

}
