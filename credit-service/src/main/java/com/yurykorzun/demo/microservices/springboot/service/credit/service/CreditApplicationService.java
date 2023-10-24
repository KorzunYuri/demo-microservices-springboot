package com.yurykorzun.demo.microservices.springboot.service.credit.service;

import com.yurykorzun.demo.microservices.springboot.commons.dto.*;
import com.yurykorzun.demo.microservices.springboot.commons.exceptions.CustomMessageExposedException;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationDeclineReason;
import com.yurykorzun.demo.microservices.springboot.commons.model.credit.CreditApplicationRegistrationStatus;
import com.yurykorzun.demo.microservices.springboot.commons.persistence.BaseEntityFields;
import com.yurykorzun.demo.microservices.springboot.service.credit.dto.CreditApplicationDto;
import com.yurykorzun.demo.microservices.springboot.service.credit.entity.CreditApplication;
import com.yurykorzun.demo.microservices.springboot.service.credit.repository.CreditApplicationRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final WebClient.Builder webClientBuilder;

    private final CreditApplicationRepository applicationRepository;

    private final Scheduler defaultScheduler;

    private final Scheduler jdbcScheduler;

    @Value("${service.customer.uri}")
    private String customerServiceUri;

    /**
     *  validate credit application dto, then process validations one by one
     *  TODO stop validation chain on error
     * @return
     */
    public Mono<CreditApplicationResponse> registerApplication(CreditApplicationDto applicationDto) {

        //  process validations
        //  example of switching thread context in Reactor. The question is: should I switch the context for jdbc operations?
        return Mono.just(applicationDto)
            .publishOn(defaultScheduler)
            .map(this::validateNewApplicationDto)
            .map(this::initializeNewApplication)
            .map(this::applyValidationChain)
            .publishOn(jdbcScheduler)
            .map(applicationRepository::save)
            .publishOn(defaultScheduler)
            .map(this::makeCreditApplicationResponse);
    }

    private CreditApplicationResponse makeCreditApplicationResponse(CreditApplication application) {
        return CreditApplicationResponse.builder()
                .id(                application.getId())
                .status(            application.getStatus())
                .declineReason(     application.getDeclineReason())
                .declineMessage(    application.getDeclineMessage())
            .build();
    }

    //  process chain of validation. // TODO validations must be isolated and should not affect each others result
    //  possible approaches:
    //    - (active) process all the validations, collect all the errors and return to the client
    //    - stop whenever there is an error
    //      for this we can introduce a specific interface representing validation task
    private CreditApplication applyValidationChain(CreditApplication application) {
        validateApplicationCustomersPresence(application);
        validateApplicationFinancialParameters(application);
        //  update application status based on validations result
        if (CreditApplicationDeclineReason.NOT_DECLINED.equals(application.getDeclineReason())) {
            application.setStatus(CreditApplicationRegistrationStatus.ACCEPTED);
        }
        return application;
    }

    /**
     *  make a map of request parameters to be used in a GET call to confirm customer presence in the customer-service
     *  @param dto - Customer provided by a client call
     *  @return MultiValueMap with parameters
     */
    private MultiValueMap<String, String> getCustomerPresenceRequestParams(CustomerDto dto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (dto.getId()         != null) params.put(BaseEntityFields.ID,        List.of(dto.getId()));
        if (dto.getPassportId() != null) params.put(CustomerFields.PASSPORT_ID, List.of(dto.getPassportId()));
        if (dto.getFirstName()  != null) params.put(CustomerFields.FIRST_NAME,  List.of(dto.getFirstName()));
        if (dto.getLastName()   != null) params.put(CustomerFields.LAST_NAME,   List.of(dto.getLastName()));
        return params;
    }

    /**
     *  validation step 1: check presented fields
     */
    private CreditApplicationDto validateNewApplicationDto(CreditApplicationDto dto) {
        if (dto.getId() != null) {
            throw new CustomMessageExposedException("Id should not be provided when registering new entity");
        }
        return dto;
    }

    /**
     *  validation step 2: check applicant and guarantor`s info
     */
    private CreditApplication validateApplicationCustomersPresence(
            final CreditApplication application) {

        List<CustomerPresenceResponse> responses = retrieveCustomerPresenceInfo(application);

        CreditApplicationRegistrationStatus status = CreditApplicationRegistrationStatus.VALIDATING;
        CreditApplicationDeclineReason declineReason = null;
        String declineMessage = "";

        if (responses.size() == 0) {
            status = CreditApplicationRegistrationStatus.      ON_HOLD;
            declineReason = CreditApplicationDeclineReason.    PROCESSING_ERROR;
            declineMessage = "Failed to retrieve customers` data from customers-service";
        } else {
            //  stream API is for sure not necessary when you process a collection with two members
            //  but it`s only demo after all

            //  collect distinct set containing statuses of validated customers
            Set<CustomerPresenceStatus> presences = responses.stream()
                .map(CustomerPresenceResponse::getPresenceStatus)
                .collect(Collectors.toSet());

            //  make a decision based on worst customer status
            if (presences.stream().allMatch(r -> r.equals(CustomerPresenceStatus.PRESENT))) {
                //  validation passed
            } else if (presences.contains(CustomerPresenceStatus.NOT_FOUND)) {
                status          = CreditApplicationRegistrationStatus.  DECLINED;
                declineReason   = CreditApplicationDeclineReason.       INFO_MISMATCH;
                declineMessage  = "Either applicant or guarantor were not found on the system";
            } else if (presences.contains(CustomerPresenceStatus.INFO_MISMATCH)) {
                status          = CreditApplicationRegistrationStatus.  ON_HOLD;
                declineReason   = CreditApplicationDeclineReason.       INFO_MISMATCH;
                declineMessage  = "Either applicant or guarantor has info mismatch compared to found on system";
            } else {
                //  this branch should never be activated, so mark a logic error
                status          = CreditApplicationRegistrationStatus.  ON_HOLD;
                declineReason   = CreditApplicationDeclineReason.       PROCESSING_ERROR;
                declineMessage  = "Undefined error in processing logic";
            }
        }
        application.setStatus(status);
        if (declineReason != null) {
            application.setDeclineReason(declineReason);
            application.setDeclineMessage(declineMessage);
        }
        return application;
    }

    private List<CustomerPresenceResponse> retrieveCustomerPresenceInfo(CreditApplication application) {
        List<CustomerDto> customersToValidate = List.of(application.getCustomer(), application.getGuarantor());
        return Flux.fromIterable(customersToValidate)
            .flatMap(customerDto -> {
                var params = getCustomerPresenceRequestParams(customerDto);
                return webClientBuilder.build().get()
                        .uri(
                                String.format("%s/api/v1/customer/presence", customerServiceUri),
                                uriBuilder -> uriBuilder.queryParams(params).build())
                        .retrieve()
                        .bodyToMono(CustomerPresenceResponse.class);
            })
            .collectList().block();
    }

    private CreditApplication validateApplicationFinancialParameters(
            final CreditApplication application) {
        //  do nothing
        return application;
    }

    private CreditApplication initializeNewApplication(CreditApplicationDto dto) {
        CreditApplication application = fromDto(dto);
        application.setStatus(CreditApplicationRegistrationStatus.VALIDATING);
        return application;
    }

    private CreditApplication fromDto(CreditApplicationDto dto) {
        return CreditApplication.builder()
                .id(            dto.getId())
                .status(CreditApplicationRegistrationStatus.NEW)
                .customer(      dto.getCustomer())
                .guarantor(     dto.getGuarantor())
                .currency(      dto.getCurrency())
                .amount(        dto.getAmount())
                .periodLength(  dto.getPeriodLength())
                .periodUnit(    dto.getPeriodUnit())
            .build();
    }
}
