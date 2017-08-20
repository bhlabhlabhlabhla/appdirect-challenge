package com.appdirect.app.dto;



public class SuccessNotificationResponse extends AbstractNotificationResponse {

    private static final boolean success = true;

    private String accountIdentifier;
    private String userIdentifier;

    public SuccessNotificationResponse(String accountIdentifier, String userIdentifier) {
        super(success);
        this.accountIdentifier = accountIdentifier;
        this.userIdentifier = userIdentifier;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    @Override
    public String toString() {
        return "SuccessNotificationResponse{" +
                "accountIdentifier='" + accountIdentifier + '\'' +
                ", userIdentifier='" + userIdentifier + '\'' +
                '}';
    }
}
