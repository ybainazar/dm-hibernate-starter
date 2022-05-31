package com.hibernate;

import com.hibernate.converter.BirthdayConverter;
import com.hibernate.entity.Birthday;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;
//
//        var connection = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/dm_hibernate", "postgres", "postgres");
//        Session

        var configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());// 1st option
//        configuration.addAnnotatedClass(User.class); // 1st option
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure(); // configure(path-to-config)

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();

            var user = User.builder()
                    .username("ivan1@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthDate(new Birthday(LocalDate.of(2000, 10, 12)))
                    .role(Role.ADMIN)
                    .build();

            session.save(user);

            session.getTransaction().commit();
        }
    }
}
