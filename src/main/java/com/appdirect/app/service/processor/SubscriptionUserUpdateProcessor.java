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
import org.springframework.transaction.annotation.Transactional;

/**
 * This Implementation handles Updates of SubscriptionUser into database.
 * Here main validations are:
 *      - Check if assigned user exists or not. If it does then return Failure response with Not Found error message.
 *
 * Logic: We find the Subscription User related to Subscription based on provided Event data and we modify it as per received
 *  event and persists into our database.
 *
 * On Success it returns empty Success response.
 */
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
    @Transactional
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
