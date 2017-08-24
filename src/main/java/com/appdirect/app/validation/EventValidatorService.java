package com.appdirect.app.validation;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.domain.repository.SubscriptionUserDao;
import com.appdirect.app.dto.Error;
import com.appdirect.app.dto.Event;
import com.appdirect.app.exception.EventValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service layer of Validation Support for Event Handling.
 * This is factory class in which we can define types of validations we have to execute for type of event being processed
 */
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

    /**
     * This method validates the provided event based on event type.
     * It executed various event validator implementations to make sure event fits all the rules as per API doc.
     * If validation fails, it throws a RunTimeException
     * @param event AppDirect Event which needs to be validated
     */
    @SuppressWarnings("unchecked")
    public void validate(Event event) {

        String errorMessage = null;

        // Based on type of event do the validations
        switch (event.getType()) {

            case SUBSCRIPTION_ORDER: {
                String uuid = event.getPayload().getCompany().getUuid();
                errorMessage = String.format("Subscription with AccountIdentifier: %s already exists", uuid);
                uniqueIdValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.FORBIDDEN));
                break;
            }

            case SUBSCRIPTION_CANCEL:
            case SUBSCRIPTION_CHANGE: {
                String uuid = event.getPayload().getAccount().getAccountIdentifier();
                errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
                existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));
                break;
            }

            case USER_ASSIGNMENT: {
                String uuid = event.getPayload().getAccount().getAccountIdentifier();

                //Check if Subscription exists
                errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
                existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));

                Subscription subscription = subscriptionDao.findByAccountIdentifier(uuid);

                //Check if Max limit is reached
                errorMessage = String.format("Subscription Max Items limit reached. Cannot add more than %s items.", subscription.getMaxOrderItems());
                userLimitValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.MAX_USERS_REACHED));

                uuid = event.getPayload().getUser().getUuid();
                errorMessage = String.format("User with UUID: %s already exists", uuid);
                uniqueIdValidator.validateEvent(event, SubscriptionUser.class, uuid, subscriptionUserDao, new EventValidationFailedException(errorMessage, Error.USER_ALREADY_EXISTS));
                break;
            }

            case USER_UNASSIGNMENT:
            case USER_UPDATED: {
                String uuid = event.getPayload().getAccount().getAccountIdentifier();

                //Check if Subscription exists
                errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
                existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));

                uuid = event.getPayload().getUser().getUuid();
                errorMessage = String.format("User with UUID: %s does not exists", uuid);
                existsValidator.validateEvent(event, SubscriptionUser.class, uuid, subscriptionUserDao, new EventValidationFailedException(errorMessage, Error.USER_NOT_FOUND));
                break;
            }

            case SUBSCRIPTION_NOTICE: {
                String uuid = event.getPayload().getAccount().getAccountIdentifier();
                errorMessage = String.format("Subscription with AccountIdentifier: %s does not exists", uuid);
                existsValidator.validateEvent(event, Subscription.class, uuid, subscriptionDao, new EventValidationFailedException(errorMessage, Error.ACCOUNT_NOT_FOUND));
                break;
            }

        }
    }
}
