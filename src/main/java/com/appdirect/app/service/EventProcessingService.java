package com.appdirect.app.service;



import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Event;

public interface EventProcessingService {

    AbstractNotificationResponse processEvent(String eventUrl);

}
