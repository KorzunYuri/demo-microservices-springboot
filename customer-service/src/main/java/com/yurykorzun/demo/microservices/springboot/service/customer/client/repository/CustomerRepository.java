package com.yurykorzun.demo.microservices.springboot.service.customer.client.repository;

import com.yurykorzun.demo.microservices.springboot.service.customer.client.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByPassportId(String s);
}
