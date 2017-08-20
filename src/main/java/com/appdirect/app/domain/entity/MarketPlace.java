package com.appdirect.app.domain.entity;



import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MarketPlace extends BaseEntity {

    @Column
    private String baseUrl;

    @Column
    private String partner;

    public MarketPlace() {
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "MarketPlace{" +
                "baseUrl='" + baseUrl + '\'' +
                ", partner='" + partner + '\'' +
                '}';
    }
}
