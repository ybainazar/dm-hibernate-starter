//package com.hibernate.entity;
//
//import lombok.*;
//
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//import javax.persistence.PrimaryKeyJoinColumn;
//import java.util.List;
//
//@Data
//@EqualsAndHashCode(callSuper=false)
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
////@DiscriminatorValue("manger")
//@PrimaryKeyJoinColumn(name = "id")
//public class Manager extends User{
//
//    private String projectName;
//
//    @Builder
//    public Manager(Long id, PersonalInfo personalInfo, String username, Role role, String info, Company company, Profile profile, List<UserChat> userChats, String projectName) {
//        super(id, personalInfo, username, role, info, company, profile, userChats);
//        this.projectName = projectName;
//    }
//}
