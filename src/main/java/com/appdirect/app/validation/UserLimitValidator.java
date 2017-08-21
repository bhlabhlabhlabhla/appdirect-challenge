package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserLimitValidator implements EventValidator {

    @Override
    public void validateEvent(Event event, Class tClazz, Serializable serializable, JpaRepository jpaRepository, RuntimeException e) {
        Subscription subscription = ((SubscriptionDao)jpaRepository).findByAccountIdentifier(event.getPayload().getAccount().getAccountIdentifier());
        if(subscription.getItems()!=null && subscription.getItems().size() + 1 > subscription.getMaxOrderItems())
            throw e;

    }
}
