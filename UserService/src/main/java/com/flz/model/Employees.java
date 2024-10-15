package com.flz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Employees extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "inside_number", nullable = false)
    private String insideNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "identy_number", nullable = false, unique = true)
    private String idNumber;

    @Column(name = "contract_period", nullable = false)
    private int contractPeriod;

    @Column(name = "graduation_status")
    private String graduationStatus;

    @Column(name = "graduation_year")
    private LocalDateTime graduationYear;

    /**
     * -----------------------------------------------------------------------------------
     * Employees       Positions
     * M                M
     */
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "employees_position",
            joinColumns = {@JoinColumn(name = "id", nullable = false)},
            inverseJoinColumns = { @JoinColumn(name = "POSITION_ID", nullable = false)}
    )
    private Set<Positions> positions = new HashSet<>();

    /**
     * -----------------------------------------------------------------------------------
     * Employees       Address
     * M                M
     */
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "employees_address",
            joinColumns = {@JoinColumn(name = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id", nullable = false)}
    )
    private Set<Address> address = new HashSet<>();

    /**-----------------------------------------------------------------------------------
     Employee      User
     1          1
    */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Users user;

}
