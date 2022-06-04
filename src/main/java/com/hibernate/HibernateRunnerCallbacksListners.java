package com.hibernate;

import com.hibernate.entity.Payment;
import com.hibernate.interceptor.GlobalInterceptor;
import com.hibernate.util.HibernateUtil;
import com.hibernate.util.TestDataImporter;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Transactional
public class HibernateRunnerCallbacksListners {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory
                     .withOptions()
                     .interceptor(new GlobalInterceptor())
                     .openSession()) {
            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            var payment = session.find(Payment.class, 1L);

            session.getTransaction().commit();
        }
    }
}

