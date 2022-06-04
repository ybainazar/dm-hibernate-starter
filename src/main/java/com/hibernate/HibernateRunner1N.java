package com.hibernate;

import com.hibernate.entity.*;
import com.hibernate.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.graph.GraphSemantic;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class HibernateRunner1N {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
//            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            var userGraph = session.createEntityGraph(User.class);
            userGraph.addAttributeNodes("company", "userChats");
            var userChatsSubGraph = userGraph.addSubgraph("userChats", UserChat.class);
            userChatsSubGraph.addAttributeNodes("chat");

//            var user = session.get(User.class, 1L);
            Map<String, Object> properties = Map.of(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("WithCompanyAndChat"));
            var user = session.find(User.class, 1L, properties);
//            System.out.println(user.getPayments().size());
//            System.out.println(user.getCompany().getName());
//
//            var users = session.createQuery("select u from User u", User.class)
//                    .list();


            var users = session.createQuery("select u from User u", User.class)
//                    .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("WithCompanyAndChat"))
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), userGraph)
                    .list();
//
//            users.forEach(user -> System.out.println(user.getPayments().size()));
//            users.forEach(user -> System.out.println(user.getCompany().getName()));


            session.getTransaction().commit();
        }
    }
}

