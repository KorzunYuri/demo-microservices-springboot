package com.yurykorzun.demo.microservices.springboot.commons.dto;

import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationDeclineReason;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationRegistrationStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreditApplicationResponse {
    private String id;
    private CreditApplicationRegistrationStatus status;
    private CreditApplicationDeclineReason declineReason;
}
