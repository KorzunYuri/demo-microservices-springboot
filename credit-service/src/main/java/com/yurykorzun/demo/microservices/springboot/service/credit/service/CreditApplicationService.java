package com.yurykorzun.demo.microservices.springboot.service.credit.service;

import com.yurykorzun.demo.microservices.springboot.service.credit.dto.CreditApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final WebClient.Builder webClientBuilder;

    @Value("${service.customer.uri}")
    private String customerServiceUri;

    public String registerApplication(CreditApplicationDto dto) {
        return "registered";
    }
}
