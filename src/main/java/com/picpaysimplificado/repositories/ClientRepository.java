package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
   Optional<Client> findUserByDocument(String document);
   Optional<Client> findClientById(UUID id);
}
