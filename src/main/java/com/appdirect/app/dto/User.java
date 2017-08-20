package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AbstractUser {

    @Override
    public String toString() {
        return "User"+super.toString();
    }
}
