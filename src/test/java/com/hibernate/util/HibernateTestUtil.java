package com.hibernate.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateTestUtil {

    private static final PostgreSQLContainer<?> posgtres = new PostgreSQLContainer<>("postgres:13");

    static {
        posgtres.start();
    }

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = HibernateUtil.buildConfiguration();
        configuration.setProperty("hibernate.connection.url", posgtres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", posgtres.getUsername());
        configuration.setProperty("hibernate.connection.password", posgtres.getPassword());
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
