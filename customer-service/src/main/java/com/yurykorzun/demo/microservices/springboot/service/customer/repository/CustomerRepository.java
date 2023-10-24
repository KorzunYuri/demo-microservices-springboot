package com.yurykorzun.demo.microservices.springboot.service.customer.repository;

import com.yurykorzun.demo.microservices.springboot.service.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByPassportId(String s);
}
