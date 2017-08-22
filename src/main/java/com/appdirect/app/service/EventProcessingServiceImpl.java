package com.appdirect.app.service;

import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.*;
import com.appdirect.app.service.processor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Service;



@Service
public class EventProcessingServiceImpl implements EventProcessingService {

    protected Logger logger = LoggerFactory.getLogger(EventProcessingServiceImpl.class);

    @Autowired
    protected SubscriptionCreateProcessor subscriptionCreateProcessor;

    @Autowired
    protected SubscriptionCancelProcessor subscriptionCancelProcessor;

    @Autowired
    protected SubscriptionChangeProcessor subscriptionChangeProcessor;

    @Autowired
    protected SubscriptionUserAssignmentProcessor subscriptionUserAssignmentProcessor;

    @Autowired
    protected SubscriptionUserUnAssignmentProcessor subscriptionUserUnAssignmentProcessor;

    @Autowired
    protected SubscriptionNoticeProcessor subscriptionNoticeProcessor;

    @Autowired
    protected SubscriptionUserUpdateProcessor subscriptionUserUpdateProcessor;

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Autowired
    private ProtectedResourceDetails protectedResourceDetails;

    @Override
    public AbstractNotificationResponse processEvent(String eventUrl) {
        logger.info("Notification Received: {}", eventUrl);

        Event event = fetchEvent(eventUrl); // fetch
        if(event == null) throw new IllegalArgumentException("Event cannot be empty");
        logger.info("Fetched Event: {}", event);

        // for ping tests
        if(Flag.STATELESS.equals(event.getFlag())) return new SuccessNotificationResponse();

        AbstractNotificationResponse response = getEventProcessor(event).processEvent(event);

        logger.info("Notification Response: {}", response);

        return response;
    }

    private Event fetchEvent(String eventUrl) {
        logger.info("Fetching event at URL: {}", eventUrl);
        OAuthRestTemplate rest = new OAuthRestTemplate(protectedResourceDetails);
        return rest.getForObject(eventUrl, Event.class);
    }

    private EventProcessor getEventProcessor(Event event) {
        if(EventType.SUBSCRIPTION_ORDER.equals(event.getType()))
            return subscriptionCreateProcessor;
        else if(EventType.SUBSCRIPTION_CANCEL.equals(event.getType()))
            return subscriptionCancelProcessor;
        else if(EventType.SUBSCRIPTION_CHANGE.equals(event.getType()))
            return subscriptionChangeProcessor;
        else if(EventType.USER_ASSIGNMENT.equals(event.getType()))
            return subscriptionUserAssignmentProcessor;
        else if(EventType.USER_UNASSIGNMENT.equals(event.getType()))
            return subscriptionUserUnAssignmentProcessor;
        else if(EventType.SUBSCRIPTION_NOTICE.equals(event.getType()))
            return subscriptionNoticeProcessor;
        else if(EventType.USER_UPDATED.equals(event.getType()))
            return subscriptionUserUpdateProcessor;

        logger.error("Invalid type received. Cannot find appropriate implementation.");
        throw new UnsupportedOperationException();
    }

}
