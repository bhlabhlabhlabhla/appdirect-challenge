package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.dto.Event;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionEntityConverter implements EntityConverter<Subscription, Event> {


    @Override
    public Subscription toEntity(Event event) {
        Subscription subscription = new Subscription();
        subscription.setAccountIdentifier(event.getCreator().getUuid());
        return subscription;
    }
}
