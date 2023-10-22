package com.yurykorzun.demo.microservices.springboot.service.customer.entity;

import com.yurykorzun.demo.microservices.springboot.commons.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CUSTOMER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Customer extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "passport_id")
    private String passportId;

}
