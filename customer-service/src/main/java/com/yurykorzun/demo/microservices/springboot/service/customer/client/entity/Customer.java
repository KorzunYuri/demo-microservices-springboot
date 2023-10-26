package com.yurykorzun.demo.microservices.springboot.service.customer.client.entity;

import com.yurykorzun.demo.microservices.springboot.commons.persistence.BaseEntity;
import com.yurykorzun.demo.microservices.springboot.service.customer.client.dto.CustomerFields;
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

    @Column(name = CustomerFields.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = CustomerFields.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = CustomerFields.PASSPORT_ID)
    private String passportId;

}
