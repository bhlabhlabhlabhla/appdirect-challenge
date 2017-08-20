package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.MarketPlace;
import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.dto.Event;

public class MarketPlaceEntityConverter implements EntityConverter<MarketPlace, com.appdirect.app.dto.MarketPlace> {


    @Override
    public MarketPlace toEntity(com.appdirect.app.dto.MarketPlace dto) {
        if(dto == null) return null;
        MarketPlace entity = new MarketPlace();
        entity.setBaseUrl(dto.getBaseUrl());
        entity.setPartner(dto.getPartner());
        return entity;
    }
}
