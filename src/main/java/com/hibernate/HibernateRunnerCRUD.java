package com.hibernate;

import com.hibernate.dao.PaymentRepository;
import com.hibernate.dao.UserRepository;
import com.hibernate.mapper.CompanyReadMapper;
import com.hibernate.mapper.UserReadMapper;
import com.hibernate.service.UserService;
import com.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

@Transactional
public class HibernateRunnerCRUD {

    public static void main(String[] args) throws SQLException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
//            var session = sessionFactory.getCurrentSession();
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            session.beginTransaction();

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);

            var userRepository = new UserRepository(session);
            var paymentRepository = new PaymentRepository(session);

            var userService = new UserService(userRepository, userReadMapper);

            userService.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();

        }
    }
}

