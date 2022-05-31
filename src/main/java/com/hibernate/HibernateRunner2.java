package com.hibernate;

import com.hibernate.entity.User;
import com.hibernate.util.HibernateUtil;

import java.sql.SQLException;

public class HibernateRunner2 {
    public static void main(String[] args) throws SQLException {

        var user = User.builder()
                .username("ivan@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .build();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
//                User -> Transient
                session1.saveOrUpdate(user);
//                User -> Persistent (user inside persistentContext)

                session1.getTransaction().commit();


            }
//                User -> Detached (session.close())

            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("Sveta");
//                session2.delete(user);

//                session2.refresh(user); // BD-MAIN:: get(user) from DB by Id -> apply changes to user in class: refresh object from DB
//                User freshUser = session2.get(User.class, user.getUsername());
//                user.setLastname(freshUser.getLastname());
//                user.setFirstname(freshUser.getFirstname());


                session2.merge(user); // CLASS-MAIN:: get(user) from BD by Id -> apply changes to user in BD: merge object in DB
//                User mergedUser = session2.get(User.class, user.getUsername());
//                mergedUser.setLastname(user.getLastname());
//                mergedUser.setFirstname(user.getFirstname());
//                User -> Persistent (user inside persistentContext) (select query comes)


                session2.getTransaction().commit();
//                User -> Removed
            }
        }
    }
}
