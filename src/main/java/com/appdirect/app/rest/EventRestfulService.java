package com.appdirect.app.rest;



import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.service.EventProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v1/integration/events")
public class EventRestfulService {

    protected Logger logger = LoggerFactory.getLogger(EventRestfulService.class);

    @Qualifier("eventProcessingServiceImpl")
    @Autowired
    protected EventProcessingService eventProcessingService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AbstractNotificationResponse> processEvent(@RequestParam("eventUrl") String eventUrl) {
        logger.info("Notification Received: {}", eventUrl);
        AbstractNotificationResponse response = eventProcessingService.processEvent(eventUrl);
        logger.info("Notification Response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
