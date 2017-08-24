package com.appdirect.app.service.processor;



import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Event;

/**
 * Event Processor Interface which dictates the contract of event processing.
 * All Implementation follows following Flow:
 *      - Execution of validations
 *      - Conversion from DTO to Entity objects
 *      - Persisting of entity object into database.

 */
public interface EventProcessor {

    /**
     * Processing of Fetched event with respective implementation based on Event Type
     * @param event AppDirect Event
     * @return Success or Failure Response
     */
    AbstractNotificationResponse processEvent(Event event);

}
