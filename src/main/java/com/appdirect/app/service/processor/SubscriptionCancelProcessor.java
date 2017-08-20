package com.appdirect.app.service.processor;


import com.appdirect.app.converter.EntityConverterService;
import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionCancelProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionCancelProcessor.class);

    @Autowired
    protected EntityConverterService entityConverterService;


    @Override
    @Transactional
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling Subscription Cancel Event");

        validate(event);

        return null;
    }

    private void validate(Event event) {
//        EventValidator validator = new UniqueIdValidator(subscriptionDao);
//        String errorMessage = String.format("Subscription with Creator UUID: %s already exists", event.getCreator().getUuid());
//        validator.validateEvent(event, Subscription.class, event.getCreator().getUuid(), new EventValidationFailedException(errorMessage, com.appdirect.app.dto.Error.FORBIDDEN));
    }
}
