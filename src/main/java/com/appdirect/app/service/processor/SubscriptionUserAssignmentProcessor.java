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
