package com.zion.school.model.security;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    private String description;


//    @ManyToMany(
//            fetch = FetchType.LAZY,
//            mappedBy = "roles"
//    )
//    private Set<User> users = new HashSet<>();

    @Transient
    private Set<User> users = new HashSet<>();

    public Role() {

    }
}