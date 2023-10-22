package com.yurykorzun.demo.microservices.springboot.service.customer.service;

import com.yurykorzun.demo.microservices.springboot.service.customer.dto.CustomerDto;
import com.yurykorzun.demo.microservices.springboot.service.customer.entity.Customer;
import com.yurykorzun.demo.microservices.springboot.service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        //  TODO check that id is not provided, there are no another user with this passportId etc.
    }

    /*
        dto -> entity and vise versa mapping
        TODO use mapper, e.g. Dozer, Mapstruct, ModelMapper. Exclude BaseEntity tracking fields (createdAt, updatedAt)
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
