package com.hibernate;

import com.hibernate.entity.Birthday;
import com.hibernate.entity.Company;
import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.User;
import com.hibernate.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner3 {

    public static void main(String[] args) throws SQLException {

        var company = Company.builder()
                .name("Google")
                .build();

        var user = User.builder()
                .username("ivan3@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Ivanov")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                        .build())
                .company(company)
                .build();

        log.info("User entity is in transient state: {}", user);

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

//                session.save(company);
//                session.save(user);
                var user1 = session.get(User.class, 1L);
                var company1 = user1.getCompany();
                company1.getId();

                session.getTransaction().commit();
            }
        }
    }
}

