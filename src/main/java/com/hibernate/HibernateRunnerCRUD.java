package com.hibernate;

import com.hibernate.dao.CompanyRepository;
import com.hibernate.dao.PaymentRepository;
import com.hibernate.dao.UserRepository;
import com.hibernate.dto.UserCreateDto;
import com.hibernate.entity.PersonalInfo;
import com.hibernate.entity.Role;
import com.hibernate.interceptor.TransactionInterceptor;
import com.hibernate.mapper.CompanyReadMapper;
import com.hibernate.mapper.UserCreateMapper;
import com.hibernate.mapper.UserReadMapper;
import com.hibernate.service.UserService;
import com.hibernate.util.HibernateUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunnerCRUD {

    @Transactional
    public static void main(String[] args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
//            var session = sessionFactory.getCurrentSession();
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

//            session.beginTransaction();
            var userRepository = new UserRepository(session);
            var companyRepository = new CompanyRepository(session);
            var paymentRepository = new PaymentRepository(session);
            var transactionInterceptor = new TransactionInterceptor(sessionFactory);

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);
            var userCreateMapper = new UserCreateMapper(companyRepository);


//            var userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            UserService userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);



            userService.findById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Liza")
                            .lastname("Lizova")
//                            .birthDate(LocalDate.now())
                            .build(),
                    "liza2@gmail.com",
                    null,
                    Role.USER,
                    1
            );

            userService.create(userCreateDto);

//            session.getTransaction().commit();

        }
    }
}

