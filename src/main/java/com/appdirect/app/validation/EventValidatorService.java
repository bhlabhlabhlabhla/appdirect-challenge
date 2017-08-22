package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.domain.repository.SubscriptionUserDao;
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
    protected SubscriptionUserDao subscriptionUserDao;

    @Autowired
    @Qualifier("uniqueIdValidator")
    protected EventValidator uniqueIdValidator;

    @Autowired
    @Qualifier("existsValidator")
    protected EventValidator existsValidator;

    @Autowired
    @Qualifier("userLimitValidator")
    protected EventValidator userLimitValidator;

    public void validate(Event event) {

        String errorMessage = null;

        if(EventType.SUBSCRIPTION_ORDER.equals(event.getType())) {
            String uuid = event.getPayload().getCompany().getUuid();
            errorMessage = String.format("Subscription with AccountIdentifier: %s already exists", uuid);
            uniqueIdValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.FORBIDDEN));
        } else if (EventType.SUBSCRIPTION_CANCEL.equals(event.getType()) || EventType.SUBSCRIPTION_CHANGE.equals(event.getType())) {
            String uuid = event.getPayload().getAccount().getAccountIdentifier();
            errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
            existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));
        } else if(EventType.USER_ASSIGNMENT.equals(event.getType())) {
            String uuid = event.getPayload().getAccount().getAccountIdentifier();

            //Check if Subscription exists
            errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
            existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));

            Subscription subscription = subscriptionDao.findByAccountIdentifier(uuid);

            //Check if Max limit is reached
            errorMessage = String.format("Subscription Max Items limit reached. Cannot add more than {} items.", subscription.getMaxOrderItems());
            userLimitValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.MAX_USERS_REACHED));

            uuid = event.getPayload().getUser().getUuid();
            errorMessage = String.format("User with UUID: %s already exists", uuid);
            uniqueIdValidator.validateEvent(event, SubscriptionUser.class, uuid, subscriptionUserDao, new EventValidationFailedException(errorMessage, Error.USER_ALREADY_EXISTS));

        } else if(EventType.USER_UNASSIGNMENT.equals(event.getType())) {
            String uuid = event.getPayload().getAccount().getAccountIdentifier();

            //Check if Subscription exists
            errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
            existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));

            uuid = event.getPayload().getUser().getUuid();
            errorMessage = String.format("User with UUID: %s does not exists", uuid);
            existsValidator.validateEvent(event, SubscriptionUser.class, uuid, subscriptionUserDao, new EventValidationFailedException(errorMessage, Error.USER_NOT_FOUND));
        } else if(EventType.SUBSCRIPTION_NOTICE.equals(event.getType())) {
            String uuid = event.getPayload().getAccount().getAccountIdentifier();
            errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
            existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));

        }
    }
}
