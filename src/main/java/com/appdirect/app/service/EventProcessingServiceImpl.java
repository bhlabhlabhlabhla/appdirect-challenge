package com.appdirect.app.service;

import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Event;
import com.appdirect.app.dto.EventType;
import com.appdirect.app.service.processor.EventProcessor;
import com.appdirect.app.service.processor.SubscriptionCancelProcessor;
import com.appdirect.app.service.processor.SubscriptionCreateProcessor;
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
    protected SubscriptionDao subscriptionDao;
    @Autowired
    private ProtectedResourceDetails protectedResourceDetails;

    @Override
    public AbstractNotificationResponse processEvent(String eventUrl) {
        Event event = fetchEvent(eventUrl); // fetch
        if(event == null) throw new IllegalArgumentException("Event cannot be empty");
        logger.info("Fetched Event: {}", event);
        AbstractNotificationResponse response = getEventProcessor(event).processEvent(event);
        printCurrentDbRecord();
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

        return null;
    }

    private void printCurrentDbRecord() {
        logger.info("____ Current Subscriptions ____");
        subscriptionDao.findAll().forEach(subscription -> {
            logger.info(subscription.toString());
        });
    }
}
