package com.appdirect.app.converter;

import com.appdirect.app.dto.Creator;
import com.appdirect.app.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EntityConverterService {

    protected Logger logger = LoggerFactory.getLogger(EntityConverterService.class);

    @Autowired
    @Qualifier("subscriptionEntityConverter")
    protected EntityConverter subscriptionEntityConverter;

    @Autowired
    @Qualifier("subscriptionUserEntityConverter")
    protected EntityConverter subscriptionUserEntityConverter;

    public Object convert(Object dto) {
        if(dto instanceof Event)
            return subscriptionEntityConverter.toEntity(dto);
        else if(dto instanceof Creator)
            return subscriptionUserEntityConverter.toEntity(dto);
        else
            return null;
    }
}
