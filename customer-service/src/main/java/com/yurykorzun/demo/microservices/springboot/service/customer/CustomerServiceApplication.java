package com.yurykorzun.demo.microservices.springboot.service.customer;

import com.yurykorzun.demo.microservices.springboot.commons.web.exceptions.ReactiveWebExceptionsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ReactiveWebExceptionsConfig.class) // example of importing beans from outer module
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
