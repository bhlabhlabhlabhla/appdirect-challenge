package com.appdirect.app;

import com.appdirect.app.domain.repository.SubscriptionDao;
import com.appdirect.app.dto.Event;
import com.appdirect.app.service.processor.SubscriptionCancelProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class SubscriptionCancelEventTest {

    @Autowired
    protected SubscriptionDao subscriptionDao;
    @Autowired
    protected SubscriptionCancelProcessor subscriptionCancelProcessor;
    Logger logger = LoggerFactory.getLogger(SubscriptionCreateEventTest.class);

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    //@Test
    public void testSubscriptionCreateEvent() {
        Event mockEvent = createMockEvent();
        subscriptionCancelProcessor.processEvent(mockEvent);
        printCurrentDbRecord();
    }

    private void printCurrentDbRecord() {
        logger.info("____ Current Subscriptions ____");
        subscriptionDao.findAll().forEach(subscription -> {
            logger.info(subscription.toString());
        });
    }

    private Event createMockEvent() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "{\n" +
                "    \"type\": \"SUBSCRIPTION_CANCEL\",\n" +
                "    \"marketplace\": {\n" +
                "      \"baseUrl\": \"https://www.acme.com\",\n" +
                "      \"partner\": \"APPDIRECT\"\n" +
                "    },\n" +
                "    \"creator\": {\n" +
                "      \"address\": {\n" +
                "        \"city\": \"Sommerville\",\n" +
                "        \"country\": \"US\",\n" +
                "        \"firstName\": \"Test\",\n" +
                "        \"fullName\": \"Test User\",\n" +
                "        \"lastName\": \"User\",\n" +
                "        \"phone\": \"5305556465\",\n" +
                "        \"state\": \"MA\",\n" +
                "        \"street1\": \"55 Grove St\",\n" +
                "        \"zip\": \"02144\"\n" +
                "      },\n" +
                "      \"email\": \"testuser@testco.com\",\n" +
                "      \"firstName\": \"Test\",\n" +
                "      \"language\": \"en\",\n" +
                "      \"lastName\": \"User\",\n" +
                "      \"locale\": \"en-US\",\n" +
                "      \"openId\": \"https://www.acme.com/openid/id/d124bf8b-0b0b-40d3-831b-b7f5a514d487\",\n" +
                "      \"uuid\": \"d124bf8b-0b0b-40d3-831b-b7f5a514d487\"\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "      \"account\": {\n" +
                "        \"accountIdentifier\": \"385beb51-51ae-4ffe-8c05-3f35a9f99825\",\n" +
                "        \"status\": \"ACTIVE\"\n" +
                "      }\n" +
                "    }\n" +
                "}";
        try {
            Event event = objectMapper.readValue(jsonInString, Event.class);
            return event;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
