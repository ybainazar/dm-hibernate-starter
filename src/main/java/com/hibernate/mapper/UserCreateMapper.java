package com.hibernate.mapper;

import com.hibernate.dao.CompanyRepository;
import com.hibernate.dto.UserCreateDto;
import com.hibernate.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User>{

    private final CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .username(object.username())
                .info(object.info())
                .role(object.role())
                .company(companyRepository.findById(object.companyId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
