package com.appdirect.app.service.processor;

import com.appdirect.app.converter.EntityConverterService;
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
public class SubscriptionUserUnAssignmentProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionUserUnAssignmentProcessor.class);

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
        logger.info("Handling User UnAssignment Event");

        eventValidatorService.validate(event);

        String userUUID = event.getPayload().getUser().getUuid();
        String accountIdentifier = event.getPayload().getAccount().getAccountIdentifier();

        SubscriptionUser subscriptionUser = subscriptionUserDao.findByUserUUIDAndSubscriptionAccountIdentifier(userUUID, accountIdentifier);
        subscriptionUserDao.delete(subscriptionUser);

        logger.info("UnAssigned User with UUID: {} to subscription with AccountIdentifier: {}", userUUID, accountIdentifier);

        return new SuccessNotificationResponse();
    }
}
