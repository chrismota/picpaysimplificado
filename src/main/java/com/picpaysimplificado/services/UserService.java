package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDto;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new RuntimeException("Usuário do tipo lojista não está autorizado a realizar transações");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    public User findUserById(UUID id) {
        return this.repository.findUserById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User createUser(UserDto data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
