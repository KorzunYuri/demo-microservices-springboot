package com.yurykorzun.demo.microservices.springboot.service.customer.controller;

import com.yurykorzun.demo.microservices.springboot.commons.config.ReactiveWebExceptionsConfig;
import com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.service.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Import(ReactiveWebExceptionsConfig.class) // example of importing beans from outer module
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @ResponseBody
    public Mono<CustomerDto> getById(@PathVariable String id) {
        return Mono.just(this.customerService.getById(id));
    }

    @PostMapping
    @ResponseBody
    public Mono<String> addCustomer(@RequestBody CustomerDto dto) throws Exception {
        return Mono.just(this.customerService.add(dto));
    }

}