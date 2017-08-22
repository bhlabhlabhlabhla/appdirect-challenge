package com.appdirect.app.rest;


import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Error;
import com.appdirect.app.dto.ErrorNotificationResponse;
import com.appdirect.app.exception.EventValidationFailedException;
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
        AbstractNotificationResponse response = null;
        try {
            response = eventProcessingService.processEvent(eventUrl);
        } catch (EventValidationFailedException e) {
          logger.error("Validation failed while processing event", e);
          response = e.getErrorNotificationResponse();
        } catch (UnsupportedOperationException e) {
            logger.error("Unable to process received event", e);
            response = new ErrorNotificationResponse(Error.CONFIGURATION_ERROR);
        } catch (Exception e) {
            logger.error("Unable to process received event", e);
            response = new ErrorNotificationResponse(Error.UNKNOWN_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
