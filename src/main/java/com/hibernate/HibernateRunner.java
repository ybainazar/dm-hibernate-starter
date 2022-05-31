package com.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;



//
//        var connection = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/dm_hibernate", "postgres", "postgres");
//        Session

        var configuration = new Configuration();
        configuration.configure(); // configure(path-to-config)

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            System.out.println("OK");
        }
    }
}
