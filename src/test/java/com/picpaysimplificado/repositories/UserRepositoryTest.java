package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from database")
    void findUserByDocumentCase1() {
        String document = "999999999901";
        UserDto data = new UserDto("Fernanda", "Teste", document, new BigDecimal(10), "test@gmail.com", "senha", UserType.COMMON);
        this.createClient(data);

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

  @Test
    @DisplayName("Should not get user successfully from database when user doesn't exist")
    void findUserByDocumentCase2() {
        String document = "999999999901";

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createClient(UserDto data){
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}