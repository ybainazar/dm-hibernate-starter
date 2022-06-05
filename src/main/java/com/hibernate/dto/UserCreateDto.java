package com.hibernate.dto;

import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.Role;
import com.hibernate.validation.UpdateCheck;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto(
        @Valid
        PersonalInfo personalInfo,
        @NotNull
        String username,
        String info,
        @NotNull(groups = UpdateCheck.class)
        Role role,
        Integer companyId) {
}
