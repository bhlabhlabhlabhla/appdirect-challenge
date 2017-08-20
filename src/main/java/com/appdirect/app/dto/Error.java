package com.appdirect.app.dto;


public enum Error {

    USER_ALREADY_EXISTS("Subscription already purchased"),
    USER_NOT_FOUND("Unable to find user: %s"),
    ACCOUNT_NOT_FOUND("Account %s not found"),
    MAX_USERS_REACHED("Maximum assigning limit has reached. Max limit is: %d"),
    UNAUTHORIZED("Authorization required."),
    OPERATION_CANCELED("Operation Cancelled"),
    CONFIGURATION_ERROR("Configuration Error"),
    INVALID_RESPONSE("Invalid Response"),
    PENDING("Request is still Pending"),
    FORBIDDEN("Operation is not allowed"),
    BINDING_NOT_FOUND("Binding not found"),
    TRANSPORT_ERROR("Network issue: Unable to reach server"),
    UNKNOWN_ERROR("Unexpected error occurred");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
