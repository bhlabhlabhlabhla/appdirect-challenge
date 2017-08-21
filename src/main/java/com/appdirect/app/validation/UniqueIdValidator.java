package com.appdirect.app.validation;


import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.domain.repository.SubscriptionUserDao;
import com.appdirect.app.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UniqueIdValidator implements EventValidator {

    @Override
    public void validateEvent(Event event, Class aClass, Serializable id, JpaRepository repository, RuntimeException exception) {
        if(Subscription.class.equals(aClass)) {
            if(((SubscriptionDao) repository).findByAccountIdentifier((String) id) != null) {
                throw exception;
            }
        } else if(SubscriptionUser.class.equals(aClass)) {
            if(((SubscriptionUserDao) repository).findByUserUUID((String) id) != null) {
                throw exception;
            }
        } else {
            if(repository.findOne(id) != null) {
                throw exception;
            }
        }


    }

}
