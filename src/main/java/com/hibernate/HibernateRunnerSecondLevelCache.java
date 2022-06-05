package com.hibernate;

import com.hibernate.entity.Payment;
import com.hibernate.entity.User;
import com.hibernate.util.HibernateUtil;
import org.hibernate.ReplicationMode;
import org.hibernate.envers.AuditReaderFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Transactional
public class HibernateRunnerSecondLevelCache {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            User user = null;
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();


                user = session.find(User.class, 1L);
                var user1 = session.find(User.class, 1L);

                var payment = session.createQuery("select p from Payment p where p.receiver.id = :userID", Payment.class)
                        .setParameter("userID", 1L)
                        .setCacheable(true)
                        .getResultList();

                System.out.println(sessionFactory.getStatistics());

                session.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                var user2 = session2.find(User.class, 1L);

                session2.getTransaction().commit();
            }
        }
    }
}

