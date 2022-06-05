package com.hibernate.dto;

import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.Role;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto(
        @Valid
        PersonalInfo personalInfo,
        @NotNull
        String username,
        String info,
        Role role,
        Integer companyId) {
}
