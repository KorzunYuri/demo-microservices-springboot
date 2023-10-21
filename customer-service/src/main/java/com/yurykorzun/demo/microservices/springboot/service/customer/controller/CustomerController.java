package com.yurykorzun.demo.microservices.springboot.service.customer.controller;

import com.yurykorzun.demo.microservices.springboot.commons.exception.ResourceNotFoundException;
import com.yurykorzun.demo.microservices.springboot.service.customer.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.service.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus
    @PostMapping
    @ResponseBody
    public Mono<String> addCustomer(CustomerDto dto) {
        return Mono.just(this.customerService.add(dto));
    }

    @GetMapping
    @ResponseBody
    public Mono<CustomerDto> findCustomerByPassportId(@RequestParam String passportId) {
    // TODO public Mono<CustomerDto> findCustomerByPassportId(@RequestParam Map<String, String> params) {
        return Mono.just(
                this.customerService.findByPassportId(passportId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("customer with passportId %s not found", passportId)))
        );
    }

}
