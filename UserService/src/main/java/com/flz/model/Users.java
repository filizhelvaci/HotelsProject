package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    private byte userType;

    /**
     * -----------------------------------------------------------------------------------
     * Users           Address
     * M                M
     */
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (name = "USERS_ADDRESS",
            joinColumns = { @JoinColumn(name = "ID", nullable = false)  },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID", nullable = false)}
    )
    private Set<Address> address = new HashSet<>();

    /**
     * -----------------------------------------------------------------------------------
     * Users            Customers
     * 1                 1
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customers customer;

    /**
     * -----------------------------------------------------------------------------------
     * Users            Employees
     * 1                 1
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employees employee;

}
