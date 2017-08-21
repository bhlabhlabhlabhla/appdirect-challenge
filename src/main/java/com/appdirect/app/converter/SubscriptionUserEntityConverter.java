package com.appdirect.app.converter;


import com.appdirect.app.domain.entity.SubscriptionUser;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionUserEntityConverter implements EntityConverter<SubscriptionUser, com.appdirect.app.dto.Creator> {


    @Override
    public void toEntity(SubscriptionUser entity, com.appdirect.app.dto.Creator dto) {
        if(dto == null) return;
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAdministrator(false);
        entity.setLanguage(dto.getLanguage());
        entity.setOpenId(dto.getOpenId());
        entity.setUuid(dto.getUuid());
    }
}
