package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private String editionCode;
    private String addonOfferingCode;
    private String pricingDuration;
    private List<Item> items;

    public Order() {
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getAddonOfferingCode() {
        return addonOfferingCode;
    }

    public void setAddonOfferingCode(String addonOfferingCode) {
        this.addonOfferingCode = addonOfferingCode;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "editionCode='" + editionCode + '\'' +
                ", addonOfferingCode='" + addonOfferingCode + '\'' +
                ", pricingDuration='" + pricingDuration + '\'' +
                ", items=" + items +
                '}';
    }
}
