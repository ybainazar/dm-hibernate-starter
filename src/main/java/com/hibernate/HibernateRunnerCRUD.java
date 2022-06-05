package com.hibernate;

import com.hibernate.dao.PaymentRepository;
import com.hibernate.util.HibernateUtil;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Transactional
public class HibernateRunnerCRUD {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                var paymentRepository = new PaymentRepository(sessionFactory);

                paymentRepository.findById(1L).ifPresent(System.out::println);

                session.getTransaction().commit();
            }
        }
    }
}

