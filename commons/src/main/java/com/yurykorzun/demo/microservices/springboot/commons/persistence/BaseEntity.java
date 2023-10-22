package com.yurykorzun.demo.microservices.springboot.commons.persistence;

import com.yurykorzun.demo.microservices.springboot.commons.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

/**
 * basic entity for all persisted classes
 * <property>createdAt</property> and <property>updatedAt</property> are made to track record lifecycle in db
 * @author Yury Korzun
 */

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Id
    @Column(name = BaseEntityFields.ID, updatable = false, nullable = false)
    private String id = UuidGenerator.randomUuid();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = BaseEntityFields.CREATED_AT, updatable = false, nullable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = BaseEntityFields.UPDATED_AT, updatable = false, nullable = false)
    private Date updatedAt = new Date();

}