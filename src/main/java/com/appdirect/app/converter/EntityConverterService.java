package com.appdirect.app.converter;

import com.appdirect.app.dto.Creator;
import com.appdirect.app.dto.Event;
import com.appdirect.app.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Service layer for Dto to Entity conversion.
 *
 * Here based on provided DTO type we handle the conversion to entity type object.
 */
@Service
public class EntityConverterService {

    protected Logger logger = LoggerFactory.getLogger(EntityConverterService.class);

    @Autowired
    @Qualifier("subscriptionEntityConverter")
    protected EntityConverter subscriptionEntityConverter;

    @Autowired
    @Qualifier("subscriptionUserEntityConverter")
    protected EntityConverter subscriptionUserEntityConverter;

    /**
     * Main Conversion entry point method which handles conversion based on dto type.
     * @param entity Entity object
     * @param dto DTO object
     * @param <E> Entity type
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> void convert(E entity, Object dto) {
        if(dto instanceof Event)
            subscriptionEntityConverter.toEntity(entity, dto);
        else if(dto instanceof Creator || dto instanceof User)
            subscriptionUserEntityConverter.toEntity(entity, dto);

    }
}
