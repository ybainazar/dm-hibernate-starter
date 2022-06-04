package com.hibernate;

import com.hibernate.entity.User;
import com.hibernate.entity.UserChat;
import com.hibernate.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.graph.GraphSemantic;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class HibernateRunnerTransactions {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();


            session.getTransaction().commit();
        }
    }
}

