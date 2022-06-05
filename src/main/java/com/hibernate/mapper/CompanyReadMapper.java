package com.hibernate.mapper;

import com.hibernate.dto.CompanyReadDto;
import com.hibernate.entity.Company;
import org.hibernate.Hibernate;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto mapFrom(Company object) {
//        Hibernate.initialize(object.getLocales()); // because Map is Persistent Map
        return new CompanyReadDto(
                object.getId(),
                object.getName(),
                object.getLocales()
        );
    }
}
