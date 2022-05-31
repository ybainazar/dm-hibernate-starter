## Entity Lifecycle

```java
// SessionFactory{  BasicTypes <- MetaModel -> [Entities(User with @Id)  -->  Entity Persisters(EntityPersister responsible for CRUD)] -> CRUD }
// Sessions{ Session <-> PersistentContext(Cache)}
```


```java
/**
 * EntityLifecycle
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
```