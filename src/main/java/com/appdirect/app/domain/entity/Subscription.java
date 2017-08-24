package com.appdirect.app.domain.entity;


import com.appdirect.app.domain.entity.type.SubscriptionState;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
public class Subscription extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String accountIdentifier;

    @Column(unique = true, nullable = false)
    private String companyUuid;

    @Column
    private String companyCountry;

    @Column
    private String companyName;

    @Column
    private String companyPhoneNumber;

    @Column
    private String companyWebsite;

    @Column
    private String baseUrl;

    @Column
    private String partner;

    @Column
    private String editionCode;

    @Column
    private String pricingDuration;

    @Column
    private Long maxOrderItems;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "subscription")
    private Set<OrderItem> items;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionState state;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "subscription")
    private Set<SubscriptionUser> subscriptionUsers = new HashSet<>();

    @Transient
    private String adminUser;

    public Subscription() {
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
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

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    public Long getMaxOrderItems() {
        return maxOrderItems;
    }

    public void setMaxOrderItems(Long maxOrderItems) {
        this.maxOrderItems = maxOrderItems;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public SubscriptionState getState() {
        return state;
    }

    public void setState(SubscriptionState state) {
        this.state = state;
    }

    public Set<SubscriptionUser> getSubscriptionUsers() {
        return subscriptionUsers;
    }

    public void setSubscriptionUsers(Set<SubscriptionUser> subscriptionUsers) {
        this.subscriptionUsers = subscriptionUsers;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "accountIdentifier='" + accountIdentifier + '\'' +
                ", companyUuid='" + companyUuid + '\'' +
                ", companyCountry='" + companyCountry + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyPhoneNumber='" + companyPhoneNumber + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", partner='" + partner + '\'' +
                ", editionCode='" + editionCode + '\'' +
                ", pricingDuration='" + pricingDuration + '\'' +
                ", maxOrderItems=" + maxOrderItems +
                ", items=" + (items!=null && items.size()>0 ? new ArrayList<>(items).stream().map(OrderItem::toString) : "") +
                ", state=" + state +
                ", subscriptionUsers=" + subscriptionUsers +
                '}';
    }

    public String getAdminUser() {
        if(getSubscriptionUsers()!=null && getSubscriptionUsers().size()>0) {
           List<SubscriptionUser> subscriptionUserList = new ArrayList<>(subscriptionUsers);
           Optional<SubscriptionUser> adminUser =  subscriptionUserList.stream().filter(SubscriptionUser::getAdministrator).findAny();
           if(adminUser.isPresent())
               return String.format("%s %s", adminUser.get().getFirstName(), adminUser.get().getLastName());
            else return "";
        }
        return "";
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }
}
