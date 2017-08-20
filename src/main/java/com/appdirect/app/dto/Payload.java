package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    private User user;
    private Account account;
    private Company company;
    private Order order;
    private AddonInstance addonInstance;
    private AddonBinding addonBinding;
    private Notice notice;
    private Map<String, String> configuration;

    public Payload() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public AddonInstance getAddonInstance() {
        return addonInstance;
    }

    public void setAddonInstance(AddonInstance addonInstance) {
        this.addonInstance = addonInstance;
    }

    public AddonBinding getAddonBinding() {
        return addonBinding;
    }

    public void setAddonBinding(AddonBinding addonBinding) {
        this.addonBinding = addonBinding;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "user=" + user +
                ", account=" + account +
                ", company=" + company +
                ", order=" + order +
                ", addonInstance=" + addonInstance +
                ", addonBinding=" + addonBinding +
                ", notice=" + notice +
                ", configuration=" + configuration +
                '}';
    }
}
