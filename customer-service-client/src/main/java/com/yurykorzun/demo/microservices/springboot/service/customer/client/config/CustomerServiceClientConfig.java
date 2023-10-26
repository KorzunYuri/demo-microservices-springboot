package com.yurykorzun.demo.microservices.springboot.service.customer.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackages = "com.yurykorzun.demo.microservices.springboot.service.customer.client")
public class CustomerServiceClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder customerServiceClientWebClientBuilder() {
        return WebClient.builder();
    }



}
