package com.appdirect.app.exception;



import com.appdirect.app.dto.Error;
import com.appdirect.app.dto.ErrorNotificationResponse;

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
