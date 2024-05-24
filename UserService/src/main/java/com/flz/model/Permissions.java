package com.flz.model;

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

    @ManyToMany(mappedBy = "permissions")
    private Set<Positions> positions = new HashSet<>();

    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles = new HashSet<>();

}
