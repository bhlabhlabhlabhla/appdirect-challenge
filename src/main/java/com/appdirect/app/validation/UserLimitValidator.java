package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * This implementation validates if the UsersLimit has been reached or not during user assignment event.
 *
 * We check the max limit on Subscription and compare with items being added. If exceeds then we throw provided RuntimeException
 *
 */
@Service
public class UserLimitValidator implements EventValidator {

    @Override
    public void validateEvent(Event event, Class tClazz, Serializable serializable, CrudRepository jpaRepository, RuntimeException e) {
        Subscription subscription = ((SubscriptionDao)jpaRepository).findByAccountIdentifier(event.getPayload().getAccount().getAccountIdentifier());
        if(subscription.getItems()!=null && subscription.getItems().size() > subscription.getMaxOrderItems())
            throw e;

    }
}
