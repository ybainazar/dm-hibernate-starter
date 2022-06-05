package com.hibernate.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "users")
@Builder
@BatchSize(size = 3)
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Companies")
public class Company implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy(clause = "username DESC, lastname ASC") // DB sorting
//    @OrderBy(value = "username DESC, personalInfo.lastname ASC")
//    @OrderColumn(name = "id") // not to use
//    private Set<User> users = new HashSet<>();

//    @SortNatural // in-memory sorting by changing to TreeSet and adding Comparable to User
//    private Set<User> users = new TreeSet<>();

    @NotAudited
    @MapKey(name = "username")
    @SortNatural
    private Map<String, User> users = new TreeMap<>();
// orphanRemoval = true -> what to do with users if we manipulate with Set<User> users. If we delete user from users -> remove from DB

    @NotAudited
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
//    @AttributeOverride(name = "lang", column = @Column(name = "lang")) // can be overrided
    private List<LocaleInfo> locales = new ArrayList<>();

    public void addUser(User user) {
//        users.add(user);
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }
}
