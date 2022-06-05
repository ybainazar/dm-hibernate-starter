package com.hibernate.dto;

import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          String info,
                          Role role,
                          CompanyReadDto company) {
}
