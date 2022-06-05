package com.hibernate;

import com.hibernate.entity.Payment;
import com.hibernate.interceptor.GlobalInterceptor;
import com.hibernate.util.HibernateUtil;
import com.hibernate.util.TestDataImporter;
import org.hibernate.ReplicationMode;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Date;

@Transactional
public class HibernateRunnerCallbacksListners {

    public static void main(String[] args) throws SQLException {

//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory
//                     .withOptions()
//                     .interceptor(new GlobalInterceptor())
//                     .openSession()) {
//            TestDataImporter.importData(sessionFactory);
//            session.beginTransaction();
//
//            var payment = session.find(Payment.class, 1L);
//
//            session.getTransaction().commit();
//        }


        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                var payment = session.find(Payment.class, 1L);
                payment.setAmount(payment.getAmount() + 10);

                session.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                var auditReader = AuditReaderFactory.get(session2);
//                auditReader.find(Payment.class, 1L, 1L);
                var oldPayment = auditReader.find(Payment.class, 1L, new Date(1654415160524L));
                session2.replicate(oldPayment, ReplicationMode.OVERWRITE);


                session2.getTransaction().commit();
            }
        }
    }
}

