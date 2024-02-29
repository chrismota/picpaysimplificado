package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.Client;
import com.picpaysimplificado.domain.user.ClientType;
import com.picpaysimplificado.dtos.ClientDTO;
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
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from database")
    void findUserByDocumentCase1() {
        String document = "999999999901";
        ClientDTO data = new ClientDTO("Fernanda", "Teste", document, new BigDecimal(10), "test@gmail.com", "senha", ClientType.COMMON);
        this.createClient(data);

        Optional<Client> result = this.clientRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

  @Test
    @DisplayName("Should not get user successfully from database when user doesn't exist")
    void findUserByDocumentCase2() {
        String document = "999999999901";

        Optional<Client> result = this.clientRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private Client createClient(ClientDTO data){
        Client newClient = new Client(data);
        this.entityManager.persist(newClient);
        return newClient;
    }
}