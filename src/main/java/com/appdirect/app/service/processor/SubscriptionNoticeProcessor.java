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
import org.springframework.transaction.annotation.Transactional;

/**
 * This Implementation handles Notice alerts of Subscription into database.
 * Here main validations are:
 *      - Check if subscription exists or not. If it does then return Failure response with Not Found error message.
 *
 * Logic: We find the subscription based on event data and we change the SubscriptionState field on Subscription.
 *        If delivered notice was of type 'Closed' then we delete it from our database.
 *
 * On Success it returns empty Success response.
 */
@Service
public class SubscriptionNoticeProcessor implements EventProcessor {

    protected Logger logger = LoggerFactory.getLogger(SubscriptionNoticeProcessor.class);

    @Autowired
    protected EventValidatorService eventValidatorService;

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Override
    @Transactional
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
