package com.yurykorzun.demo.microservices.springboot.service.customer.service;

import com.yurykorzun.demo.microservices.springboot.service.customer.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.service.customer.model.Customer;
import com.yurykorzun.demo.microservices.springboot.service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public String add(CustomerDto dto) {
        //  TODO introduce validation approach: better on controller level
        validateNewCustomer(dto);
        return customerRepository.save(fromDto(dto)).getId();
    }

    private void validateNewCustomer(CustomerDto dto) throws IllegalArgumentException {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id should not be provided");
        }
        if (customerRepository.findByPassportId(dto.getPassportId()).isPresent()) {
            throw new IllegalArgumentException("User with the passport ID provided already exists");
        }
    }

    /*
        customer search
        TODO? implement a method for searching base on different combinations of fields provided
        e.g. search by id if present, or else by passportId, or else by firstname and lastname etc.
     */
    public Optional<CustomerDto> findByPassportId(String idCode) {
        return customerRepository.findByPassportId(idCode).map(this::toDto);
    }

    /*
        dto -> entity and vise versa mapping
        TODO use mapper, e.g. Dozer, Mapstruct, ModelMapper
        TODO handle sensitive info
        e.g. sometimes we don`t want passportId to be exposed
     */
    private Customer fromDto(CustomerDto dto) {
        return Customer.builder()
                .firstName(         dto.getFirstName()      )
                .lastName(          dto.getLastName()       )
                .passportId(        dto.getPassportId()     )
            .build();
    }

    private CustomerDto toDto(Customer src) {
        return CustomerDto.builder()
                .id(                src.getId()             )
                .firstName(         src.getFirstName()      )
                .lastName(          src.getLastName()       )
                .passportId(        src.getPassportId()     )
            .build();
    }

}
