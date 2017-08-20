package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.dto.Event;

public class SubscriptionEntityConverter implements EntityConverter<Subscription, Event> {


    @Override
    public Subscription toEntity(Event event) {
        Subscription subscription = new Subscription();
//        subscription.setCompany(new CompanyEntityConverter().toEntity(event.getPayload().getCompany()));
//        subscription.setMarketPlace(new MarketPlaceEntityConverter().toEntity(event.getMarketPlace()));
//        subscription.setOrder(new OrderEntityConverter().toEntity(event.getPayload().getOrder()));
        subscription.setAccountIdentifier(event.getCreator().getUuid());
        return subscription;
    }
}
