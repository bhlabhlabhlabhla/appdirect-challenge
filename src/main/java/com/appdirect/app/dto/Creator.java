package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Creator extends AbstractUser {

    @Override
    public String toString() {
        return "Creator"+super.toString();
    }
}
