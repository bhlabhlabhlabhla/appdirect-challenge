package com.appdirect.app.dto;



public abstract class AbstractNotificationResponse {

    private boolean success;

    public AbstractNotificationResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
