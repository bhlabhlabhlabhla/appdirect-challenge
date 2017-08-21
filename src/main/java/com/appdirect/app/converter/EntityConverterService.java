package com.appdirect.app.converter;

import com.appdirect.app.dto.Creator;
import com.appdirect.app.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class EntityConverterService {

    protected Logger logger = LoggerFactory.getLogger(EntityConverterService.class);

    @Autowired
    @Qualifier("subscriptionEntityConverter")
    protected EntityConverter subscriptionEntityConverter;

    @Autowired
    @Qualifier("subscriptionUserEntityConverter")
    protected EntityConverter subscriptionUserEntityConverter;

    public <E extends Serializable> void convert(E entity, Object dto) {
        if(dto instanceof Event)
            subscriptionEntityConverter.toEntity(entity, dto);
        else if(dto instanceof Creator)
            subscriptionUserEntityConverter.toEntity(entity, dto);

    }
}
