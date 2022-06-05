package com.hibernate.dto;

import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo,
                            String username,
                            String info,
                            Role role,
                            Integer companyId) {
}
