package com.hibernate.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static com.hibernate.util.StringUtils.SPACE;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company",
//        "profile",
        "userChats", "payments"})
@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "dm", typeClass = JsonBinaryType.class)
public class User implements Comparable<User>, BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Type(type = "dm")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // company_id
    private Company company;

//    @OneToOne(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    @Builder.Default
//    @BatchSize(size = 3) // works with collections -> should be List/Set
//    Nothing: 1 + N -> 1 + 5
//    batchSize=3: 1 + N -> 1 + 5/3 -> 3
    @Fetch(FetchMode.SUBSELECT) // only over collection (OneToMany)
//    1 + N -> 1 + 1 -> 2
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    public String fullName() {
        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
    }
}





//package com.hibernate.entity;
//
//import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
//import lombok.*;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.TypeDef;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static com.hibernate.util.StringUtils.SPACE;
//
//@NamedQuery(name= "findUserByName", query = "select u from User u " +
//                                            " left join u.company c " +
//                                            " where u.personalInfo.firstname = :firstname and c.name = :companyName " +
//                                            " order by u.personalInfo.lastname desc ")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(of = "username") // natural keys
//@ToString(exclude = {"company", "profile", "userChats"})
//@Entity
//@Table(name = "users", schema = "public")
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
////@Inheritance(strategy = InheritanceType.JOINED)
//@Builder
////@DiscriminatorColumn(name = "type")
//public class User implements Comparable<User>, BaseEntity<Long>{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Embedded
//    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
//    private PersonalInfo personalInfo;
//
//
//    @Column(unique = true)
//    private String username;
//
////    @Transient // not to use this column in hibernate
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
////    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
//    @Type(type = "jsonb")
//    private String info;
//
//    @ManyToOne(fetch = FetchType.LAZY) // not null constraints
//    @JoinColumn(name = "company_id") // if not using, rule -> company + _ + @Id = company_id
//    private Company company;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Profile profile;
//
////    @Builder.Default
//    @OneToMany(mappedBy = "user")
//    private List<UserChat> userChats = new ArrayList<>();
//
//    @Builder.Default
//    @OneToMany(mappedBy = "receiver")
//    private List<Payment> payments = new ArrayList<>();
//
//
//    public String fullName() {
//        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
//    }
//
//    @Override
//    public int compareTo(User o) {
//        return username.compareTo(o.username);
//    }
//}
