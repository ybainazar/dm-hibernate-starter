package com.hibernate.dto;

import com.hibernate.entity.LocaleInfo;

import java.util.List;


public record CompanyReadDto(Integer id,
                             String name,
                             List<LocaleInfo> locales) {
}
