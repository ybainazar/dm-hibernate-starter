package com.hibernate;

import com.hibernate.entity.*;
import com.hibernate.util.HibernateTestUtil;
import com.hibernate.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.hibernate.usertype.UserType;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    @Test
    void checkHql() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            String name = "Ivan";
//            var users = session.createQuery(
//                    "select u from User u where u.personalInfo.firstname = ?1", User.class)
//                    .setParameter(1, name)
//                    .list();

//            var users = session.createQuery(
//                            "select u from User u " +
//                            " left join u.company c " +
//                            " where u.personalInfo.firstname = :firstname and c.name = :companyName " +
//                            " order by u.personalInfo.lastname desc ", User.class)
//                    .setParameter("firstname", name)
//                    .setParameter("companyName", "Google")
//                    .list();

            var users = session.createNamedQuery("findUserByName", User.class)
                    .setParameter("firstname", name)
                    .setParameter("companyName", "Google")
                    .setHint(QueryHints.HINT_FETCH_SIZE, "50")
                    .list();

            var countRows = session.createQuery("update User u set u.role = 'ADMIN'")
                    .executeUpdate();

            var executeUpdate = session.createNativeQuery("select * from users where firstname = 'Ivan'", User.class)
                    .executeUpdate();


            session.getTransaction().commit();
        }
    }

    @Test
    void checkH2() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            var google = Company.builder()
//                    .name("Google")
//                    .build();
//            session.save(google);
//
//            var programmer = Programmer.builder()
//                    .username("ivan@gmail.com")
//                    .language(Language.JAVA)
//                    .company(google)
//                    .build();
//            session.save(programmer);
//
//            var manager = Manager.builder()
//                    .username("sveta@gmail.com")
//                    .projectName("Starter")
//                    .company(google)
//                    .build();
//            session.save(manager);
//
//            session.flush();
//
//            session.clear();
//
//            var programmer1 = session.get(Programmer.class, 1L);
//            var manager1 = session.get(User.class, 2L);


            session.getTransaction().commit();
        }
    }

    @Test
    void localeInfo() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = session.get(Company.class, 1);
//            company.getLocales().add(LocaleInfo.of("ru", "Описание на русском"));
//            company.getLocales().add(LocaleInfo.of("en", "English description"));
            company.getUsers().forEach((k, v) -> System.out.println(v));

            session.getTransaction().commit();
        }
    }


    @Test
    void checkManyToMany() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = session.get(User.class, 7L);
            var chat = session.get(Chat.class, 1L);

            var userChat = UserChat.builder()
//                    .createdAt(Instant.now())
//                    .createdBy(user.getUsername())
                    .build();

            userChat.setUser(user);
            userChat.setChat(chat);

            session.save(userChat);


            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            var user = User.builder()
//                    .username("test2@gmail.com")
//                    .build();

            var profile = Profile.builder()
                    .street("Abaya 5")
                    .language("RU")
                    .build();

//            profile.setUser(user);
//
//            session.save(user);


            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrhanRemoval() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.getReference(Company.class, 6);
//            company.getUsers().removeIf(user -> user.getId().equals(7L));


            session.getTransaction().commit();
        }
    }


    @Test
    void checkLazyInitialization() {
        Company company = null;
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.get(Company.class, 6);


            session.getTransaction().commit();
        }
        var users = company.getUsers();
        System.out.println(users.size());
    }


    @Test
    void deleteCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 6);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = Company.builder()
                .name("Facebook")
                .build();

//        var user = User.builder()
//                .username("sve@gmail.com")
//                .build();

//        user.setCompany(company);
//        company.getUsers().add(user);
//        company.addUser(user);

        session.save(company);


        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.get(Company.class, 1);

        session.getTransaction().commit();
    }

    @Test
    void checkGetReflectionApi() throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        var resultSet = preparedStatement.executeQuery();
        resultSet.getString("firstname");
        resultSet.getString("lastname");

        var clazz = User.class;
        var constructor = clazz.getConstructor();
        var user = constructor.newInstance();
        var username = clazz.getDeclaredField("username");
        username.setAccessible(true);
        username.set(user, resultSet.getString("firstname"));

    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
//        var user = User.builder()
//                .username("ivan@gmail.com")
//                .firstname("Ivan")
//                .lastname("Ivanov")
//                .birthDate(LocalDate.of(2000, 10, 12))
//                .age(22)
//                .build();
//
//        String SQL = """
//                  insert
//                  into
//                  %s
//                  (%s)
//                  values
//                  (%s)
//                """;
//        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
//                .orElse(user.getClass().getName());
//
//        var declaredFields = user.getClass().getDeclaredFields();
//
//        var columnNames = Arrays.stream(declaredFields)
//                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(Collectors.joining(", "));
//
//        var columnValues = Arrays.stream(declaredFields)
//                .map(field -> "?")
//                .collect(Collectors.joining(", "));
//
//        System.out.println(SQL.formatted(tableName, columnNames, columnValues));
//
//        Connection connection = null;
//        var preparedStatement = connection.prepareStatement(SQL.formatted(tableName, columnNames, columnValues));
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            preparedStatement.setObject(1, declaredField.get(user));
//        }
    }
}