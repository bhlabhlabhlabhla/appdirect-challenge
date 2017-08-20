package com.appdirect.app.dto;



public class ErrorNotificationResponse extends AbstractNotificationResponse {

    private static final boolean success = false;

    private Error error;

    public ErrorNotificationResponse(Error error) {
        super(success);
        this.error = error;
    }


    public Error getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorNotificationResponse{" +
                "error=" + error +
                '}';
    }
}
