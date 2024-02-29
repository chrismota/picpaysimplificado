package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.Client;
import com.picpaysimplificado.domain.user.ClientType;
import com.picpaysimplificado.dtos.ClientDTO;
import com.picpaysimplificado.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public void validateTransaction(Client sender, BigDecimal amount) throws Exception {
        if(sender.getClientType() == ClientType.MERCHANT) {
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transações");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public Client findClientById(UUID id) throws Exception {
        return this.repository.findClientById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public Client createClient(ClientDTO data){
        Client newClient = new Client(data);
        this.saveClient(newClient);
        return newClient;
    }

    public void saveClient(Client client){
        this.repository.save(client);
    }

    public List<Client> getAllClients() {
        return this.repository.findAll();
    }
}
