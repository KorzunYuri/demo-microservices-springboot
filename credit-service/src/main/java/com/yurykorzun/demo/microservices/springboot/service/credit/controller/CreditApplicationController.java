package com.yurykorzun.demo.microservices.springboot.service.credit.controller;

import com.yurykorzun.demo.microservices.springboot.commons.config.ReactiveWebExceptionsConfig;
import com.yurykorzun.demo.microservices.springboot.commons.dto.CreditApplicationResponse;
import com.yurykorzun.demo.microservices.springboot.service.credit.dto.CreditApplicationDto;
import com.yurykorzun.demo.microservices.springboot.service.credit.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/credit/application")
@RequiredArgsConstructor
@Import(ReactiveWebExceptionsConfig.class)
public class CreditApplicationController {

    private final CreditApplicationService applicationService;

    @PostMapping
    @ResponseBody
    public Mono<CreditApplicationResponse> registerApplication(@RequestBody CreditApplicationDto dto) {
        return applicationService.registerApplication(dto);
    }

}
