package com.yurykorzun.demo.microservices.springboot.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationDeclineReason;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationRegistrationStatus;
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
