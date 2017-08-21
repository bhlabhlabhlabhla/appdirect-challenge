package com.appdirect.app.service.processor;


import com.appdirect.app.converter.EntityConverterService;
import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.type.SubscriptionState;
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

@Service
public class SubscriptionChangeProcessor implements EventProcessor {

    @Autowired
    protected SubscriptionDao subscriptionDao;
    @Autowired
    protected SubscriptionUserDao subscriptionUserDao;
    @Autowired
    protected EntityConverterService entityConverterService;
    @Autowired
    protected EventValidatorService eventValidatorService;

    Logger logger = LoggerFactory.getLogger(SubscriptionChangeProcessor.class);

    @Override
    @Transactional
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling Subscription Change Event");

        // validations
        eventValidatorService.validate(event);

        Subscription subscription = subscriptionDao.findByAccountIdentifier(event.getPayload().getAccount().getAccountIdentifier());
        entityConverterService.convert(subscription, event);
        subscription.setState(SubscriptionState.ACTIVE);
        subscriptionDao.save(subscription);
        logger.info("Subscription updated for Creator UUID: {} with Subscription Id: {}", subscription.getAccountIdentifier(), subscription.getId());

        return new SuccessNotificationResponse();
    }

}
