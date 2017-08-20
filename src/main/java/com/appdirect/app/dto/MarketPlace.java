package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPlace {

    private String partner;
    private String baseUrl;

    public MarketPlace() {
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "MarketPlace{" +
                "partner='" + partner + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
