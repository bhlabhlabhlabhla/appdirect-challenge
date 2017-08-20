package com.appdirect.app.domain.entity;



import com.appdirect.app.domain.entity.type.SubscriptionState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subscription extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String accountIdentifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private MarketPlace marketPlace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Order order;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionState state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subscription")
    private Set<SubscriptionUser> subscriptionUsers = new HashSet<>();

    public Subscription() {
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
                ", company=" + company +
                ", marketPlace=" + marketPlace +
                ", order=" + order +
                ", state=" + state +
                ", subscriptionUsers=" + subscriptionUsers +
                '}';
    }
}
