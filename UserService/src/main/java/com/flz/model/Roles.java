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
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


//    //----------------------------------------------------------------
//    //  Roles          Permission
//    //     M               M
//    @ManyToMany
//    @JoinTable(
//            name = "role_permission",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
//    private Set<Permissions> permissions = new HashSet<>();
//
//
//    //----------------------------------------------------------------
//    //  Roles         Positions
//    //     M              M
//    @ManyToMany(mappedBy = "roles")
//    private Set<Positions> positions = new HashSet<>();
}
