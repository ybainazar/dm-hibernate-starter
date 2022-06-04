package com.hibernate;

import com.hibernate.entity.Birthday;
import com.hibernate.entity.Company;
import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.User;
import com.hibernate.util.HibernateUtil;
import com.hibernate.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner3 {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
//            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

//            var user = session.get(User.class, 1L);
//            System.out.println(user.getPayments().size());
//            System.out.println(user.getCompany().getName());

            var users = session.createQuery("select u from User u", User.class)
                    .list();

            users.forEach(user -> System.out.println(user.getPayments().size()));
            users.forEach(user -> System.out.println(user.getCompany().getName()));

            session.getTransaction().commit();
        }
    }
}

