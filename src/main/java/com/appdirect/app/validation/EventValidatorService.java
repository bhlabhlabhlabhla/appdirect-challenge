package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.Error;
import com.appdirect.app.dto.Event;
import com.appdirect.app.dto.EventType;
import com.appdirect.app.exception.EventValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventValidatorService {

    protected Logger logger = LoggerFactory.getLogger(EventValidatorService.class);

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Autowired
    @Qualifier("uniqueIdValidator")
    protected EventValidator uniqueIdValidator;

    @Autowired
    @Qualifier("existsValidator")
    protected EventValidator existsValidator;

    public void validate(Event event) {

        String errorMessage = null;

        if(EventType.SUBSCRIPTION_ORDER.equals(event.getType())) {
            String uuid = event.getPayload().getCompany().getUuid();
            errorMessage = String.format("Subscription with Creator UUID: %s already exists", uuid);
            uniqueIdValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.FORBIDDEN));
        } else if (EventType.SUBSCRIPTION_CANCEL.equals(event.getType()) || EventType.SUBSCRIPTION_CHANGE.equals(event.getType())) {
            String uuid = event.getPayload().getAccount().getAccountIdentifier();
            errorMessage = String.format("Subscription with Creator UUID: %s does not exists", uuid);
            existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));
        }
    }
}
