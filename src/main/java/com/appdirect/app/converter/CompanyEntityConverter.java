package com.appdirect.app.converter;



import com.appdirect.app.domain.entity.Company;

public class CompanyEntityConverter implements EntityConverter<Company, com.appdirect.app.dto.Company> {


    @Override
    public Company toEntity(com.appdirect.app.dto.Company dto) {
        if(dto == null) return null;
        Company entity = new Company();
        entity.setCompanyCountry(dto.getCountry());
        entity.setCompanyName(dto.getName());
        entity.setCompanyPhoneNumber(dto.getPhoneNumber());
        entity.setCompanyUuid(dto.getUuid());
        entity.setCompanyWebsite(dto.getWebsite());
        return entity;
    }
}
