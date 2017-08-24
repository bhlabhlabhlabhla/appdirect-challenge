package com.appdirect.app.exception;

import com.appdirect.app.dto.Error;
import com.appdirect.app.dto.ErrorNotificationResponse;

/**
 * Custom RunTimeException type which will be thrown on validation failure on Event DTO type.
 * This contains Error Message which can be set with provided constructor or if not provided it will used from Error enum.
 */
public class EventValidationFailedException extends ValidationFailedException {

    private ErrorNotificationResponse errorNotificationResponse;

    public EventValidationFailedException(String message, Error error) {
        super(message);
        this.errorNotificationResponse = new ErrorNotificationResponse(error);
    }

    public EventValidationFailedException(Error error) {
        super(error.getMessage());
        this.errorNotificationResponse = new ErrorNotificationResponse(error);
    }

    public ErrorNotificationResponse getErrorNotificationResponse() {
        return errorNotificationResponse;
    }
}
