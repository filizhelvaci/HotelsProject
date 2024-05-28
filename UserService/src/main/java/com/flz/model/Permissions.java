package com.flz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    //----------------------------------------------------------------
    //  Permission     Positions
    //     M              M
    @ManyToMany(mappedBy = "permissions")
    private Set<Positions> positions = new HashSet<>();


    //----------------------------------------------------------------
    //  Permission     Roles
    //     M              M
    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles = new HashSet<>();

}
