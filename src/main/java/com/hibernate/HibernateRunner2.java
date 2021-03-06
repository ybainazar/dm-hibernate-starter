//package com.hibernate;
//
//import com.hibernate.entity.Birthday;
//import com.hibernate.entity.PersonalInfo;
//import com.hibernate.entity.User;
//import com.hibernate.util.HibernateUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//
//@Slf4j
//public class HibernateRunner2 {
//
////    public static final Logger log = LoggerFactory.getLogger(HibernateRunner2.class); // not to write this -> user @Slf4j from Lombok
//
//
//    public static void main(String[] args) throws SQLException {
//
//        var user = User.builder()
//                .username("ivan3@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Ivan")
//                        .lastname("Ivanov")
//                        .birthDate(new Birthday(LocalDate.of(2000,1,1)))
//                        .build())
//                .build();
//
//        log.info("User entity is in transient state: {}", user);
//
//        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
//            var session1 = sessionFactory.openSession();
//            try (session1) {
//                var transaction = session1.beginTransaction();
//                log.trace("Transaction is created, {}", transaction);
//
////                User -> Transient
//                session1.saveOrUpdate(user);
//                log.trace("User is in persistent state: {}, session: {}", user, session1);
////                User -> Persistent (user inside persistentContext)
//
//                session1.getTransaction().commit();
//
//
//            }
//            log.warn("User is in detached state: {}, session is closed {}", user, session1);
////                User -> Detached (session.close())
//            try (var session = sessionFactory.openSession()) {
//                var key = PersonalInfo.builder()
//                        .firstname("Ivan")
//                        .lastname("Ivanov")
//                        .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
//                        .build();
//
//                var user1 = session.get(User.class, key);
//                System.out.println(user1);
//            }
//        } catch (Exception exception) {
//            log.error("Exception occurred", exception);
//            throw exception;
//        }
//    }
//}
//
//
////            try (var session2 = sessionFactory.openSession()) {
////                session2.beginTransaction();
////
////                user.setFirstname("Sveta");
////
////
////                session2.delete(user);
////
////                session2.refresh(user); // BD-MAIN:: get(user) from DB by Id -> apply changes to user in class: refresh object from DB
//////                User freshUser = session2.get(User.class, user.getUsername());
//////                user.setLastname(freshUser.getLastname());
//////                user.setFirstname(freshUser.getFirstname());
////
////
////                session2.merge(user); // CLASS-MAIN:: get(user) from BD by Id -> apply changes to user in BD: merge object in DB
//////                User mergedUser = session2.get(User.class, user.getUsername());
//////                mergedUser.setLastname(user.getLastname());
//////                mergedUser.setFirstname(user.getFirstname());
//////                User -> Persistent (user inside persistentContext) (select query comes)
////
////
////                session2.getTransaction().commit();
//////                User -> Removed
////            }
//
