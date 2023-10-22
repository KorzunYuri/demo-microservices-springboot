package com.yurykorzun.demo.microservices.springboot.service.customer.repository;

import com.yurykorzun.demo.microservices.springboot.service.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
