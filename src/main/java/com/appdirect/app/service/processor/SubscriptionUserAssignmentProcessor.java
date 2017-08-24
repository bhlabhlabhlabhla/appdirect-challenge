package com.appdirect.app.service.processor;

import com.appdirect.app.converter.EntityConverterService;
import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.domain.repository.SubscriptionUserDao;
import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Event;
import com.appdirect.app.dto.SuccessNotificationResponse;
import com.appdirect.app.validation.EventValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This Implementation handles Assignment of User to a Subscription into database.
 * Here main validations are:
 *      - Check if subscription exists or not. If it does then return Failure response with Not Found error message.
 *      - Check if assigned user already exists or not. If it does then return Failure response with Already Exists error message
 *
 * Logic: We find the subscription based on event data and we create a Subscription user and persist it.
 *
 * On Success it returns empty Success response.
 */
@Service
public class SubscriptionUserAssignmentProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionUserAssignmentProcessor.class);

    @Autowired
    protected EntityConverterService entityConverterService;

    @Autowired
    protected EventValidatorService eventValidatorService;

    @Autowired
    protected SubscriptionUserDao subscriptionUserDao;

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Override
    @Transactional
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling User Assignment Event");

        eventValidatorService.validate(event);

        SubscriptionUser subscriptionUser = new SubscriptionUser();
        entityConverterService.convert(subscriptionUser, event.getPayload().getUser());

        Subscription subscription = subscriptionDao.findByAccountIdentifier(event.getPayload().getAccount().getAccountIdentifier());
        subscriptionUser.setSubscription(subscription);
        subscriptionUserDao.save(subscriptionUser);

        logger.info("Assigned User with UUID: {} to subscription with AccountIdentifier: {} with SubscriptionUser Id: {}",
                subscriptionUser.getUuid(), subscription.getAccountIdentifier(), subscriptionUser.getId());

        return new SuccessNotificationResponse();
    }
}
