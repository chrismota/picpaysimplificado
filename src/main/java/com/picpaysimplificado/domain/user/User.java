package com.picpaysimplificado.domain.user;

import com.picpaysimplificado.dtos.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDto data){
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.document = data.document();
        this.balance = data.balance();
        this.userType = data.userType();
        this.password = data.password();
        this.email = data.email();
    }
}
