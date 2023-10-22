package com.yurykorzun.demo.microservices.springboot.commons.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.yurykorzun.demo.microservices.springboot.commons.persistence.exceptions")
public class PersistenceExceptionsHandlingConfig {
}
