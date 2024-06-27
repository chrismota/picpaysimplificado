package com.picpaysimplificado.domain.admin;

import com.picpaysimplificado.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "admins")
@Entity(name = "Admin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "admins_roles",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


}
