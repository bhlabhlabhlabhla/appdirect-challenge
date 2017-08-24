package com.appdirect.app.service;


import com.appdirect.app.dto.AbstractNotificationResponse;

/**
 * Entry point of Service layer.
 * Here we defined the contract on handling AppDirect event call
 */
public interface EventProcessingService {

    AbstractNotificationResponse processEvent(String eventUrl);

}
