package com.hibernate.dao;

import com.hibernate.entity.Payment;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(entityManager, Payment.class);
    }
}
