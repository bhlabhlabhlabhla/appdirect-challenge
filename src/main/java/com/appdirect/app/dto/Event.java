package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.appdirect.app.domain.entity.Subscription;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private EventType type;
    @JsonProperty("marketplace")
    private MarketPlace marketPlace;
    private String applicationUuid;
    private Flag flag;
    private String returnUrl;
    private Creator creator;
    private Payload payload;

    public Event() {
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public String getApplicationUuid() {
        return applicationUuid;
    }

    public void setApplicationUuid(String applicationUuid) {
        this.applicationUuid = applicationUuid;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", marketPlace=" + marketPlace +
                ", applicationUuid='" + applicationUuid + '\'' +
                ", flag=" + flag +
                ", returnUrl='" + returnUrl + '\'' +
                ", creator=" + creator +
                ", payload=" + payload +
                '}';
    }

    public Subscription toSubscriptionEntity() {
        return null;
    }
}
