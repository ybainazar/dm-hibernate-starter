//package com.hibernate.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Data
//@EqualsAndHashCode(callSuper = false)
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
////@DiscriminatorValue("programmer")
//@PrimaryKeyJoinColumn(name = "id")
//public class Programmer extends User {
//
//    @Enumerated(EnumType.STRING)
//    private Language language;
//
//    @Builder
//    public Programmer(Long id, PersonalInfo personalInfo, String username, Role role, String info, Company company, Profile profile, List<UserChat> userChats, Language language) {
//        super(id, personalInfo, username, role, info, company, profile, userChats);
//        this.language = language;
//    }
//}
