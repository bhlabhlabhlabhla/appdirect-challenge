package com.appdirect.app.validation;



import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public class UniqueIdValidator implements EventValidator {

    private JpaRepository jpaRepository;

    public UniqueIdValidator(JpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void validateEvent(Event event, Class aClass, Serializable id, RuntimeException exception) {
        if(Subscription.class.equals(aClass)) {
            if(((SubscriptionDao) jpaRepository).findByAccountIdentifier((String) id) != null) {
                throw exception;
            }
        } else {
            if(jpaRepository.findOne(id) != null) {
                throw exception;
            }
        }


    }

}
