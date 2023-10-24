package com.yurykorzun.demo.microservices.springboot.service.customer.service;

import com.yurykorzun.demo.microservices.springboot.commons.config.PersistenceExceptionsHandlingConfig;
import com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerPresenceResponse;
import com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerPresenceStatus;
import com.yurykorzun.demo.microservices.springboot.commons.persistence.exceptions.EntityNotFoundException;
import com.yurykorzun.demo.microservices.springboot.commons.persistence.exceptions.PersistenceExceptionsProcessor;
import com.yurykorzun.demo.microservices.springboot.service.customer.entity.Customer;
import com.yurykorzun.demo.microservices.springboot.service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import static com.yurykorzun.demo.microservices.springboot.commons.dto.CustomerFields.*;
import static com.yurykorzun.demo.microservices.springboot.commons.persistence.BaseEntityFields.*;

@Service
@RequiredArgsConstructor
@Import(PersistenceExceptionsHandlingConfig.class)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PersistenceExceptionsProcessor persistenceExceptionProcessor;

    public String add(CustomerDto dto) throws Exception {
        //  TODO introduce validation approach: better on controller level
        validateNewCustomer(dto);
        try {
            return customerRepository.save(fromDto(dto)).getId();
        } catch (Exception e) {
            throw persistenceExceptionProcessor.process(e);
        }
    }

    public CustomerDto getById(String id) {
        Customer entity = this.customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer", id));
        CustomerDto dto = toDto(entity);
        return dto;
    }


    private void validateNewCustomer(CustomerDto dto) throws IllegalArgumentException {
        //  TODO check that id is not provided, there are no another user with this passportId etc.
    }

    public CustomerPresenceResponse checkCustomer(Map<String, String> customerInfo) {

        //  filter search params
        Set<String> allowedParams = Set.of(
                ID, FIRST_NAME, LAST_NAME, PASSPORT_ID
        );
        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        customerInfo.forEach((key, value) -> {
            if (allowedParams.contains(key)) params.put(key, value);
        });

        //  prepare response
        var builder = CustomerPresenceResponse.builder();

        //  find customer
        Optional<Customer> opt = Optional.empty();
        if (params.containsKey(ID)) {
            opt = customerRepository.findById(params.get(ID));
        } else if (params.containsKey(PASSPORT_ID)) {
            opt = customerRepository.findByPassportId(params.get(PASSPORT_ID));
        }
        if (opt.isEmpty()) {
            return builder.presenceStatus(CustomerPresenceStatus.NOT_FOUND).build();
        }

        //  compare to input
        Customer customer = opt.get();
        boolean shouldExposeInfo = false;
        if (params.containsKey(PASSPORT_ID) && !customer.getPassportId().equals(params.get(PASSPORT_ID)))
        {
            builder.presenceStatus(CustomerPresenceStatus.INFO_MISMATCH);
        } else if  ((params.containsKey(FIRST_NAME) && (!params.get(FIRST_NAME).equals(customer.getFirstName())))
                ||  (params.containsKey(LAST_NAME)  && (!params.get(LAST_NAME).equals(customer.getLastName()))))
        {
            //  id and/or passport match, we can let client know the customer info
            builder.presenceStatus(CustomerPresenceStatus.INFO_MISMATCH);
            shouldExposeInfo = true;
        } else {
            //  all good
            builder.presenceStatus(CustomerPresenceStatus.PRESENT);
            shouldExposeInfo = true;
        }
        if (shouldExposeInfo) {
            builder.customer(CustomerDto.builder()
                                    .id(            customer.getId()            )
                                    .passportId(    customer.getPassportId()    )
                                    .firstName(     customer.getFirstName()     )
                                    .lastName(      customer.getLastName()      )
                                .build());
        }
        return builder.build();
    }

    /*
        dto -> entity and vise versa mapping
        TODO use mapper, e.g. Dozer, Mapstruct, ModelMapper. Exclude BaseEntity tracking fields (createdAt, updatedAt)
        TODO handle sensitive info
        e.g. sometimes we don`t want passportId to be exposed
     */
    private Customer fromDto(CustomerDto dto) {
        return Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .passportId(dto.getPassportId())
                .build();
    }

    private CustomerDto toDto(Customer src) {
        return CustomerDto.builder()
                .id(src.getId())
                .firstName(src.getFirstName())
                .lastName(src.getLastName())
                .passportId(src.getPassportId())
                .build();
    }

}

