package com.hibernate;

import com.hibernate.converter.BirthdayConverter;
import com.hibernate.entity.Birthday;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;
//
//        var connection = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/dm_hibernate", "postgres", "postgres");
//        Session

        var configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());// 1st option
//        configuration.addAnnotatedClass(User.class); // 1st option
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure(); // configure(path-to-config)

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();

//            var user = User.builder()
//                    .username("ivan3@gmail.com")
//                    .firstname("Ivan")
//                    .lastname("Ivanov")
//                    .birthDate(new Birthday(LocalDate.of(2000, 10, 12)))
//                    .role(Role.ADMIN)
//                    .info(
//                            """
//                                    {
//                                    "name": "Ivan",
//                                    "age": 25
//                                    }
//                                    """)
//                    .build();

//            session.save(user);
//            session.saveOrUpdate(user);
//            session.delete(user);


//        SessionFactory{  BasicTypes <- MetaModel -> [Entities(User with @Id)  -->  Entity Persisters(EntityPersister responsible for CRUD)] -> CRUD }
//        Sessions{ Session <-> PersistentContext(Cache)}



/**
 *  0--- new Entity()---> [Transient]
 *  |                            |
 *  |                            |
 *  Session.get             Session.save(entity)
 *  Session.createQuery     Session.saveOrUpdate(entity)
 *  |                           |
 *  |                           |
 *  |-------------------> [Persistent (Cached)] ----Session.delete----> [Removed]
 *                          |              |
 *                          |              |
 *                         evict           saveOrUpdate
 *                         clear           update
 *                         close           merge
 *                          |              |
 *                          |              |
 *                       [      Detached      ]
 *
 *
 *
 *
 * */

            var user1 = session.get(User.class, "ivan2@gmail.com");
            user1.setLastname("Petrov2"); // dirty session -> will change data in database
            session.flush(); // apply first-level cache to database
            var user2 = session.get(User.class, "ivan2@gmail.com"); // will be cached

//            session.evict(user1); // delete from cache (persistentContext --- first-level cache)
//            session.clear(); // clear all cache (hash map)
//            session.close(); // clear all cache (hash map)

            session.getTransaction().commit();
        }
    }
}
