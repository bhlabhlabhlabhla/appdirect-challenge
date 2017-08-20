package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractUser {

    private String email;
    private String firstName;
    private String language;
    private String lastName;
    private String locale;
    private String openId;
    private String uuid;
    private Address address;
    private Map<String, String> attributes;

    public AbstractUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", language='" + language + '\'' +
                ", lastName='" + lastName + '\'' +
                ", locale='" + locale + '\'' +
                ", openId='" + openId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", address=" + address +
                ", attributes=" + attributes +
                '}';
    }
}
