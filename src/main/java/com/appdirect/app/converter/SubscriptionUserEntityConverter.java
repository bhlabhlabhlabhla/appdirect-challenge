package com.appdirect.app.converter;

import com.appdirect.app.domain.entity.SubscriptionUser;
import com.appdirect.app.dto.AbstractUser;
import org.springframework.stereotype.Service;

/**
 * SubscriptionUser Entity Conversion Implementation.
 */
@Service
public class SubscriptionUserEntityConverter implements EntityConverter<SubscriptionUser, AbstractUser> {

    /**
     * Here we handle the conversion of AbstractUser type dto object to SubscriptionUser type entity object.
     * @param entity SubscriptionUser entity object
     * @param dto AbstractUser dto object
     */
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
