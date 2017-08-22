package com.appdirect.app.service.processor;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.entity.type.SubscriptionState;
import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.*;
import com.appdirect.app.validation.EventValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionNoticeProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionNoticeProcessor.class);

    @Autowired
    protected EventValidatorService eventValidatorService;

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Override
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling Subscription Notice Event");

        eventValidatorService.validate(event);

        Notice notice = event.getPayload().getNotice();
        String accountIdentifier = event.getPayload().getAccount().getAccountIdentifier();
        Subscription subscription = subscriptionDao.findByAccountIdentifier(accountIdentifier);

        if(notice.getType().equals(NoticeType.CLOSED)) {
            subscriptionDao.delete(subscription);
            logger.info("Closed notice received, so deleted subscription with accountIdentifier: {}.", accountIdentifier);
        } else {
            SubscriptionState updatedSubscriptionState = SubscriptionState.valueOf(event.getPayload().getAccount().getStatus());
            SubscriptionState oldSubscriptionState = subscription.getState();
            subscription.setState(updatedSubscriptionState);
            subscriptionDao.save(subscription);
            logger.info("Updated Subscription with AccountIdentifier: {} from OldStatus: {} to NewStatus: {}", accountIdentifier, oldSubscriptionState, updatedSubscriptionState);
        }

        return new SuccessNotificationResponse();
    }
}
