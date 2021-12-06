package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "user", schema = "gifts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role userRole;
    //joinColumns = {@JoinColumn(name = "certificate_id", referencedColumnName = "id")},
    //            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<Order> orders;

//    public User(Long id, String name, String surname, List<Order> orders) {
//        super(id);
//        this.name = name;
//        this.surname = surname;
//        this.orders = orders;
//    }
//
//    public User(String name, String surname, List<Order> orders) {
//        this.name = name;
//        this.surname = surname;
//        this.orders = orders;
//    }

}
