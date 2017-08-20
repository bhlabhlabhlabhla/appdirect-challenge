package com.ramdomfox.app;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.appdirect.app.Application;
import com.appdirect.app.dto.Event;
import com.appdirect.app.service.processor.SubscriptionCreateProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SubscriptionCreateEventTest {

    @Autowired
    protected SubscriptionCreateProcessor subscriptionCreateProcessor;

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSubscriptionCreateEvent() {
        Event mockEvent = createMockEvent();
        subscriptionCreateProcessor.processEvent(mockEvent);
    }

    private Event createMockEvent() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "{\n" +
                "    \"type\": \"SUBSCRIPTION_ORDER\",\n" +
                "    \"marketplace\": {\n" +
                "      \"baseUrl\": \"https://www.acme.com\",\n" +
                "      \"partner\": \"APPDIRECT\"\n" +
                "    },\n" +
                "    \"creator\": {\n" +
                "      \"address\": {\n" +
                "        \"firstName\": \"Test\",\n" +
                "        \"fullName\": \"Test User\",\n" +
                "        \"lastName\": \"User\"\n" +
                "      },\n" +
                "      \"email\": \"testuser@testco.com\",\n" +
                "      \"firstName\": \"Test\",\n" +
                "      \"language\": \"en\",\n" +
                "      \"lastName\": \" User\",\n" +
                "      \"locale\": \"en-US\",\n" +
                "      \"openId\": \"https://www.acme.com/openid/id/47cb8f55-1af6-5bfc-9a7d-8061d3aa0c97\",\n" +
                "      \"uuid\": \"47cb8f55-1af6-5bfc-9a7d-8061d3aa0c97\"\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "      \"company\": {\n" +
                "        \"country\": \"US\",\n" +
                "        \"name\": \"tester\",\n" +
                "        \"phoneNumber\": \"1-800-333-3333\",\n" +
                "        \"uuid\": \"385beb51-51ae-4ffe-8c05-3f35a9f99825\",\n" +
                "        \"website\": \"www.testco.com\"\n" +
                "      },\n" +
                "      \"order\": {\n" +
                "        \"editionCode\": \"Standard\",\n" +
                "        \"pricingDuration\": \"MONTHLY\",\n" +
                "        \"items\": [{\n" +
                "          \"quantity\": \"4\",\n" +
                "          \"unit\": \"USER\"\n" +
                "        }]\n" +
                "      }\n" +
                "    }\n" +
                "}";
        try {
            Event event = mapper.readValue(jsonInString, Event.class);
            return event;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
