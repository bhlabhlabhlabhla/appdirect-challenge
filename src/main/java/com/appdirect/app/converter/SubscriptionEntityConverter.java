package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.OrderItem;
import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.dto.Event;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SubscriptionEntityConverter implements EntityConverter<Subscription, Event> {


    @Override
    public Subscription toEntity(Event dto) {
        Subscription entity = new Subscription();
        entity.setAccountIdentifier(dto.getPayload().getCompany().getUuid());

        // company
        entity.setCompanyCountry(dto.getPayload().getCompany().getCountry());
        entity.setCompanyName(dto.getPayload().getCompany().getName());
        entity.setCompanyPhoneNumber(dto.getPayload().getCompany().getPhoneNumber());
        entity.setCompanyUuid(dto.getPayload().getCompany().getUuid());
        entity.setCompanyWebsite(dto.getPayload().getCompany().getWebsite());

        // marketPlate
        entity.setBaseUrl(dto.getMarketPlace().getBaseUrl());
        entity.setPartner(dto.getMarketPlace().getPartner());

        //order
        entity.setEditionCode(dto.getPayload().getOrder().getEditionCode());
        entity.setMaxOrderItems(dto.getPayload().getOrder().getItems()!=null?
                dto.getPayload().getOrder().getItems().get(0).getQuantity():0);
        entity.setPricingDuration(dto.getPayload().getOrder().getPricingDuration());
        entity.setItems(dto.getPayload().getOrder().getItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnit(item.getUnit());
            orderItem.setSubscription(entity);
            return orderItem;
        }).collect(Collectors.toSet()));

        return entity;
    }
}
