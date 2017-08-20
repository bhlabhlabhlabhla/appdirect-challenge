package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public class ExistsValidator implements EventValidator {

    protected Logger logger = LoggerFactory.getLogger(ExistsValidator.class);

    @Override
    public void validateEvent(Event event, Class tClazz, Serializable id, JpaRepository repository, RuntimeException e) {
        if(Subscription.class.equals(tClazz)) {
            if(((SubscriptionDao) repository).findByAccountIdentifier((String) id) == null) {
                throw e;
            }
        } else {
            if(repository.findOne(id) == null) {
                throw e;
            }
        }
    }
}
