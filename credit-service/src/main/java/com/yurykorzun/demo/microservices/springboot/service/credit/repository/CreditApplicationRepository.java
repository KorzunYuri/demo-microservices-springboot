package com.yurykorzun.demo.microservices.springboot.service.credit.repository;

import com.yurykorzun.demo.microservices.springboot.service.credit.model.CreditApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditApplicationRepository extends MongoRepository<CreditApplication, String> {
}
