package com.yurykorzun.demo.microservices.springboot.service.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class ReactiveConfig {

    @Value("${reactive.scheduler.max-pool-size}")
    private int connectionPoolSize;

    @Bean
    public Scheduler defaultScheduler() {
        return Schedulers.boundedElastic();
    }

    //  a scheduler for jdbc operations.
    //  There is an opinion that reactivity makes no sense when we have a blocking operation in the chain
    //  However, I at least try context switching
    @Bean Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
    }

}
