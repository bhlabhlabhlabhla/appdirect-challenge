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

/**
 * This is the main entry point of application where AppDirect server will reach-out and deliver Event URL which needs
 * to be fetched.
 *
 * From here on, event URL is fed into {@link EventProcessingService} to processing.
 *
 * Once processing is finished by service layer, an appropriate response will be sent back to AppDirect server.
 */

@Controller
@RequestMapping(path = "/api/v1/integration/events")
public class EventRestfulService {

    protected Logger logger = LoggerFactory.getLogger(EventRestfulService.class);

    @Qualifier("eventProcessingServiceImpl")
    @Autowired
    protected EventProcessingService eventProcessingService;

    /**
     * Entry Point on HTTP GET call on Path: /api/v1/integration/events.
     * @param eventUrl URL where AppDirect Event needs to be fetched
     * @return Success or Failure response with HTTP 200 status.
     */
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

        logger.info("Response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
