package com.hibernate;

import com.hibernate.entity.Payment;
import com.hibernate.entity.User;
import com.hibernate.entity.UserChat;
import com.hibernate.util.HibernateUtil;
import com.hibernate.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.jdbc.Work;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Transactional
public class HibernateRunnerTransactions {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

//            session.doWork(connection -> System.out.println(connection.getTransactionIsolation()));

//            session.createQuery("select p from Payment p")
//                    .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT) // not need to use.
//                    .setHint("timeout")
//                    .setReadOnly(true)
//                    .list();

//            session.setDefaultReadOnly(true); // no dirty checks
            session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate();

//            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 10);



            session.getTransaction().commit();
        }
    }
}

