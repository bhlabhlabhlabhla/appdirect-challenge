package com.appdirect.app.domain.entity;



import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SubscriptionOrder")
public class Order extends BaseEntity {

    @Column
    private String editionCode;

    @Column
    private String pricingDuration;

    @Column
    private Long maxOrderItems;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "order")
    private Set<OrderItem> items;

    public Order() {
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

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public Long getMaxOrderItems() {
        return maxOrderItems;
    }

    public void setMaxOrderItems(Long maxOrderItems) {
        this.maxOrderItems = maxOrderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "editionCode='" + editionCode + '\'' +
                ", pricingDuration='" + pricingDuration + '\'' +
                ", maxOrderItems=" + maxOrderItems +
                ", items=" + items +
                '}';
    }
}
