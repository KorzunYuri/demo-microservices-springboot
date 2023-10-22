package com.yurykorzun.demo.microservices.springboot.service.credit.service;

import com.yurykorzun.demo.microservices.springboot.commons.dto.*;
import com.yurykorzun.demo.microservices.springboot.commons.exceptions.CustomMessageExposedException;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationDeclineReason;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationRegistrationStatus;
import com.yurykorzun.demo.microservices.springboot.commons.persistence.BaseEntityFields;
import com.yurykorzun.demo.microservices.springboot.service.credit.dto.CreditApplicationDto;
import com.yurykorzun.demo.microservices.springboot.service.credit.entity.CreditApplication;
import com.yurykorzun.demo.microservices.springboot.service.credit.repository.CreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final WebClient.Builder webClientBuilder;

    private final CreditApplicationRepository applicationRepository;

    @Value("${service.customer.uri}")
    private String customerServiceUri;

    public Mono<CreditApplicationResponse> registerApplication(CreditApplicationDto applicationDto) {

        //  register application validating stage
        final CreditApplication application = registerNewApplication(applicationDto);

        //  check custoemrs validity
        //  TODO check financial parameters etc.
        List<CustomerDto> personsToValidate = List.of(applicationDto.getCustomer(), applicationDto.getGuarantor());
        return Flux.fromIterable(personsToValidate)
            .flatMap(dto -> {
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                if (dto.getId()         != null) params.put(BaseEntityFields.ID,        List.of(dto.getId()));
                if (dto.getPassportId() != null) params.put(CustomerFields.PASSPORT_ID, List.of(dto.getPassportId()));
                if (dto.getFirstName()  != null) params.put(CustomerFields.FIRST_NAME,  List.of(dto.getFirstName()));
                if (dto.getLastName()   != null) params.put(CustomerFields.LAST_NAME,   List.of(dto.getLastName()));
                return webClientBuilder.build().get()
                        .uri(
                                String.format("%s/api/v1/customer/presence", customerServiceUri),
                                uriBuilder -> uriBuilder.queryParams(params).build())
                        .retrieve().bodyToMono(CustomerPresenceResponse.class);
            })
            .collectList()
            .map(responses -> {
                if (responses.size() == 0) {
                    throw new CustomMessageExposedException("Failed to retrieve customers` data from customers-service");
                }
                Set<CustomerPresenceStatus> presences = responses.stream()
                        .map(CustomerPresenceResponse::getPresenceStatus)
                        .collect(Collectors.toSet());

                //  all good = accept, otherwise check problems from worst to minor, setting corresponding application statuses
                CreditApplicationRegistrationStatus finalStatus;
                CreditApplicationDeclineReason declineReason = CreditApplicationDeclineReason.NOT_DECLINED;
                if (responses.stream().allMatch(r -> r.getPresenceStatus().equals(CustomerPresenceStatus.PRESENT))) {
                    finalStatus = CreditApplicationRegistrationStatus.ACCEPTED;
                } else if (presences.contains(CustomerPresenceStatus.NOT_FOUND)) {
                    finalStatus = CreditApplicationRegistrationStatus.DECLINED;
                    declineReason = CreditApplicationDeclineReason.INFO_MISMATCH;
                } else if (presences.contains(CustomerPresenceStatus.INFO_MISMATCH)) {
                    finalStatus = CreditApplicationRegistrationStatus.ON_HOLD;
                    declineReason = CreditApplicationDeclineReason.INFO_MISMATCH;
                } else {
                    finalStatus = CreditApplicationRegistrationStatus.ON_HOLD;
                    declineReason = CreditApplicationDeclineReason.PROCESSING_ERROR;
                }
                application.setStatus(finalStatus);
                application.setDeclineReason(declineReason);

                // TODO apply blocking call in a scheduler
                applicationRepository.save(application);

                return CreditApplicationResponse.builder()
                        .id(application.getId())
                        .status(finalStatus)
                        .declineReason(declineReason)
                        .build();
            });
    }

    private CreditApplication registerNewApplication(CreditApplicationDto dto) {
        CreditApplication application = fromDto(dto);
        application = applicationRepository.save(application);
        return application;
    }

    private CreditApplication fromDto(CreditApplicationDto dto) {
        return CreditApplication.builder()
                .status(CreditApplicationRegistrationStatus.VALIDATING)
                .currency(      dto.getCurrency())
                .amount(        dto.getAmount())
                .periodLength(  dto.getPeriodLength())
                .periodUnit(    dto.getPeriodUnit())
            .build();
    }
}
