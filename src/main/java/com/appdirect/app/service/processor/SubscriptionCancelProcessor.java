package com.appdirect.app.service.processor;


import com.appdirect.app.converter.EntityConverterService;
import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
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
 * This Implementation handles Cancellation of Subscription into database.
 * Here main validations are:
 *      - Check if subscription exists or not. If it does then return Failure response with Not Found error message.
 *
 * Logic: We find the subscription based on event data and we delete it from our database.
 * On Success it returns empty Success response.
 */
@Service
public class SubscriptionCancelProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionCancelProcessor.class);

    @Autowired
    protected EntityConverterService entityConverterService;
    @Autowired
    protected EventValidatorService eventValidatorService;
    @Autowired
    protected SubscriptionDao subscriptionDao;


    @Override
    @Transactional
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling Subscription Cancel Event");

        eventValidatorService.validate(event);

        Subscription subscription = subscriptionDao.findByAccountIdentifier(event.getPayload().getAccount().getAccountIdentifier());
        subscriptionDao.delete(subscription);
        logger.info("Subscription with AccountIdentifier: {} is Cancelled", subscription.getAccountIdentifier());

        return new SuccessNotificationResponse();
    }

}
