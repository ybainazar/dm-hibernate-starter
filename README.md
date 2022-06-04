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

##  @GeneratedValue
### strategy
1. **IDENTITY** 
- common one
``` java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```
2. **SEQUENCE** 
- uses sequence generator from DB
```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
@SequenceGenerator(name = "user_generator", sequenceName = "users_id_seq", allocationSize = 1)
private Long id;
```
```sql
create sequence users_id_seq
owned by users.id;
```
3. **TABLE** 
- creates additional table to get/load IDs
```java
@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_generator")
@TableGenerator(name = "user_generator", table = "all_sequence",
        pkColumnName = "table_name", valueColumnName = "pk_value")
private Long id;
```
```sql
create table all_sequence
(
    table_name varchar(32) primary key ,
    pk_value bigint not null
)
```

## Primary key (several columns) - _better not to use_

* add @EmbeddedId
```java
@EmbeddedId
@AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
private PersonalInfo personalInfo;
```
* in PersonalInfo (Embeddable class) make it Serializable. Auto generate by IDEA
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String firstname;
    private String lastname;
    private Birthday birthDate;
}
```

## Proxy
* Can be created by using following libraries:
  * ByteBuddy (used now)
  * Javaassist (replaced clib)
  * Clib (used in first versions of Hibernate)

* unporxy
```java
var object = Hibernate.unproxy(company1);
```

* to get proxy directly 
```java
session.getReference(Company.class , 1);
```


## ManyToOne
* optional (for ref tables)
  * true (left join) 
  * false (inner join)
* FetchType (fetch = FetchType.LAZY/EAGER)
  * EAGER - returns ref (ref object add to PersistentContext(Cache))
  * LAZY - within current Session, returns by request
* Cascade (cascade = CascadeType.DETACH) 

## 1 + N problem
1. BatchSize()
2. @Fetch(FetchMode.SUBSELECT)
3. QueryFetch (fetch join)
4. FetchProfile (work only with get (session.get(User.class, 1L)))
5. EntityGraph

* Best practice:
  * avoid **@OneToOne bi-drectional**
  * use fetch type **LAZY** everywhere
  * dont prefer **@BatchSize**, **@Fetch**
  * use query **fetch** (HQL, Criteria API, Querydsl)
  * prefer **EntityGraph API** then @FetchProfile

## Transactions isolations
1. Optimistic Locks (application level)
   * `@OptimisticLocking(type = OptimisticLockType.VERSION)` -> **BETTER**! uses version for locking rows (first commit rule)
   * `@OptimisticLocking(type = OptimisticLockType.ALL)` -> uses all column in Where clause
   * `@OptimisticLocking(type = OptimisticLockType.DIRTY)` -> uses only changed columns in Where

2. Pessimistic Locks (database level)
   * PESSIMISTIC_READ: adds "for share" in a query (row level locks) 
   * PESSIMISTIC_WRITE: adds "for update" in a query (row level locks) 
   * PESSIMISTIC_FORCE_INCREMENT: need version column. "for update" + version increment

* use timeouts (session.get)

## Read-only transactions
1. Application level
   * `session.setDefaultReadOnly(true)`   -> no dirty checks (read-only)
   * `.setReadOnly(true)` -> for HQL
2. Database level
   * `session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate()` 

## Nontransactional Data Access
* Auto-commit mode
* do not use, only for READS. 