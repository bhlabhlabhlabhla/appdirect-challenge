package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.domain.repository.SubscriptionUserDao;
import com.appdirect.app.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * This implementation validates the existence of provided Entity type with ID field.
 *
 * Special handling for Subscription and SubscriptionUser Entity:
 *
 *      - We validate Subscription with AccountIdentifier field.
 *      - We validate SubscriptionUser with UUID field.
 *
 * If validation fails the method throws provided RuntimeException
 */
@Service
public class ExistsValidator implements EventValidator {

    protected Logger logger = LoggerFactory.getLogger(ExistsValidator.class);

    @Override
    public void validateEvent(Event event, Class tClazz, Serializable id, CrudRepository repository, RuntimeException e) {
        if(Subscription.class.equals(tClazz)) {
            if(((SubscriptionDao) repository).findByAccountIdentifier((String) id) == null) {
                throw e;
            }
        } else if(SubscriptionUser.class.equals(tClazz)) {
            if(((SubscriptionUserDao) repository).findByUserUUID((String) id) == null) {
                throw e;
            }
        } else {
            if(repository.findOne(id) == null) {
                throw e;
            }
        }
    }
}
