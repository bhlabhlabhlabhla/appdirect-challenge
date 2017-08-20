package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    private String accountIdentifier;
    private String parentAccountIdentifier;
    private String status;

    public Account() {
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getParentAccountIdentifier() {
        return parentAccountIdentifier;
    }

    public void setParentAccountIdentifier(String parentAccountIdentifier) {
        this.parentAccountIdentifier = parentAccountIdentifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountIdentifier='" + accountIdentifier + '\'' +
                ", parentAccountIdentifier='" + parentAccountIdentifier + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
