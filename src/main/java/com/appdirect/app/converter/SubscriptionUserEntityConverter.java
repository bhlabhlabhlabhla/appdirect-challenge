package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.Order;
import com.appdirect.app.domain.entity.OrderItem;
import com.appdirect.app.domain.entity.SubscriptionUser;

import java.util.stream.Collectors;

public class SubscriptionUserEntityConverter implements EntityConverter<SubscriptionUser, com.appdirect.app.dto.Creator> {


    @Override
    public SubscriptionUser toEntity(com.appdirect.app.dto.Creator dto) {
        if(dto == null) return null;
        SubscriptionUser entity = new SubscriptionUser();
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAdministrator(false);
        entity.setLanguage(dto.getLanguage());
        entity.setOpenId(dto.getOpenId());
        entity.setUuid(dto.getUuid());
        return entity;
    }
}
