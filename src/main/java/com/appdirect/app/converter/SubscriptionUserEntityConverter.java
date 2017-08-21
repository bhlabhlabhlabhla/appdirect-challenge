package com.appdirect.app.converter;


import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.dto.AbstractUser;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionUserEntityConverter implements EntityConverter<SubscriptionUser, AbstractUser> {


    @Override
    public void toEntity(SubscriptionUser entity, AbstractUser dto) {
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
