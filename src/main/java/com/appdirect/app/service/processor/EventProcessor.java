package com.appdirect.app.service.processor;



import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Event;

public interface EventProcessor {

    AbstractNotificationResponse processEvent(Event event);

}
