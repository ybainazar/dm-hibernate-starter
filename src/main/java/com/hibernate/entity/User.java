package com.hibernate.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User {

    @Id // should be implemented serializable
    private String username;
    private String firstname;
    private String lastname;
//    @Convert(converter = BirthdayConverter.class) // autoApply=true added as converter in configuration/Birthday class
    @Column(name = "birth_date")
    private Birthday birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;

//    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
    @Type(type = "jsonb")
    private String info;



//
//    public User() {
//    }
//
//    public User(String username, String firstname, String lastname, LocalDate birthDate, Integer age) {
//        this.username = username;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.birthDate = birthDate;
//        this.age = age;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(LocalDate birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuffer sb = new StringBuffer("User{");
//        sb.append("username='").append(username).append('\'');
//        sb.append(", firstname='").append(firstname).append('\'');
//        sb.append(", lastname='").append(lastname).append('\'');
//        sb.append(", birthDate=").append(birthDate);
//        sb.append(", age=").append(age);
//        sb.append('}');
//        return sb.toString();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return Objects.equals(username, user.username) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(birthDate, user.birthDate) && Objects.equals(age, user.age);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username, firstname, lastname, birthDate, age);
//    }
}