package com.yurykorzun.demo.microservices.springboot.service.credit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yurykorzun.demo.microservices.springboot.service.credit.model.CreditApplicationDeclineReason;
import com.yurykorzun.demo.microservices.springboot.service.credit.model.CreditApplicationRegistrationStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditApplicationResponse {
    private String id;
    private CreditApplicationRegistrationStatus status;
    private CreditApplicationDeclineReason declineReason;
    private String declineMessage;
}
