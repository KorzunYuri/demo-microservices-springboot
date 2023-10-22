package com.yurykorzun.demo.microservices.springboot.service.credit.controller;

import com.yurykorzun.demo.microservices.springboot.service.credit.dto.CreditApplicationDto;
import com.yurykorzun.demo.microservices.springboot.service.credit.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credit/application")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService applicationService;

    @PostMapping
    @ResponseBody
    public String registerApplication(@RequestBody CreditApplicationDto dto) {
        return applicationService.registerApplication(dto);
    }

}
