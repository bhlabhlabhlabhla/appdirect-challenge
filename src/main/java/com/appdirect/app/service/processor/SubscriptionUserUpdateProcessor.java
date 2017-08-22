package com.appdirect.app.service.processor;

import com.appdirect.app.converter.EntityConverterService;
import com.appdirect.app.domain.entity.SubscriptionUser;
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
public class SubscriptionUserUpdateProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionUserUpdateProcessor.class);

    @Autowired
    protected EventValidatorService eventValidatorService;

    @Autowired
    protected SubscriptionUserDao subscriptionUserDao;

    @Autowired
    protected EntityConverterService entityConverterService;

    @Override
    public AbstractNotificationResponse processEvent(Event event) {

        logger.info("Handling Subscription User Update Event");

        eventValidatorService.validate(event);


        String userUUID = event.getPayload().getUser().getUuid();
        String accountIdentifier = event.getPayload().getAccount().getAccountIdentifier();

        SubscriptionUser subscriptionUser = subscriptionUserDao.findByUserUUIDAndSubscriptionAccountIdentifier(userUUID, accountIdentifier);
        entityConverterService.convert(subscriptionUser, event.getPayload().getUser());
        subscriptionUserDao.save(subscriptionUser);
        logger.info("Updated user with uuid: {} and accountIdentifier: {}", userUUID, accountIdentifier);


        return new SuccessNotificationResponse();
    }
}
