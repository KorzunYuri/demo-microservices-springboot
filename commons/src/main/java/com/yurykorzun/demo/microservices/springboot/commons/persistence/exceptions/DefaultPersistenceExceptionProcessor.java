package com.yurykorzun.demo.microservices.springboot.commons.persistence.exceptions;

import com.yurykorzun.demo.microservices.springboot.commons.exceptions.CustomMessageExposedException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 *  TODO use AOP to intercept and catch errors of repository methods?
 */
@Component
public class DefaultPersistenceExceptionProcessor implements PersistenceExceptionsProcessor {

    @Override
    public Exception process(Exception e) {
        Exception result = e;
        if (e instanceof DataIntegrityViolationException) {
            Throwable cause = e.getCause();
            if (cause instanceof ConstraintViolationException cve) {
                result = new CustomMessageExposedException(e, String.format("Constraint violated for %s", cve.getConstraintName()));
            } else if (cause instanceof PropertyValueException pve) {
                result = new CustomMessageExposedException(e, String.format("Illegal value for property %s.%s", pve.getEntityName(), pve.getPropertyName()));
            }
        }
        return result;
    }
}
