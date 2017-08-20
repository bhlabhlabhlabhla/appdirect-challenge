package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.Company;
import com.appdirect.app.domain.entity.Order;
import com.appdirect.app.domain.entity.OrderItem;

import java.util.stream.Collectors;

public class OrderEntityConverter implements EntityConverter<Order, com.appdirect.app.dto.Order> {


    @Override
    public Order toEntity(com.appdirect.app.dto.Order dto) {
        if(dto == null) return null;
        Order entity = new Order();
        entity.setEditionCode(dto.getEditionCode());
        entity.setMaxOrderItems(-1L); // Not sure
        entity.setPricingDuration(dto.getPricingDuration());
        entity.setItems(dto.getItems().stream().map(item -> {
            OrderItem orderItem =  new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnit(item.getUnit());
            //orderItem.setOrder(entity);
            return orderItem;
        }).collect(Collectors.toSet()));

        return entity;
    }
}
