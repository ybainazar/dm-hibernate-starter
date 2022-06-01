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
  * 
