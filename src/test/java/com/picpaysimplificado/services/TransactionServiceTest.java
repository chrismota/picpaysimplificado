package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.Client;
import com.picpaysimplificado.domain.user.ClientType;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthTransactionService authService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Client sender = new Client(senderId, "Maria", "Souza", "99999999991", "maria@gmail.com", "12345", new BigDecimal(10), ClientType.COMMON);
        Client receiver = new Client(receiverId, "Joao", "Souza", "99999999992", "joao@gmail.com", "12345", new BigDecimal(10), ClientType.COMMON);

        when(clientService.findClientById(senderId)).thenReturn(sender);
        when(clientService.findClientById(receiverId)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), senderId, receiverId);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(clientService, times(1)).saveClient(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(clientService, times(1)).saveClient(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transação realizada com sucesso.");
        verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso.");
    }

    @Test
    @DisplayName("Should throw Exception when transaction is not allowed")
    void createTransactionCase2() throws Exception {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Client sender =  new Client(senderId, "Maria", "Souza", "99999999991", "maria@gmail.com", "12345", new BigDecimal(10), ClientType.COMMON);
        Client receiver = new Client(receiverId, "Joao", "Souza", "99999999992", "joao@gmail.com", "12345", new BigDecimal(10), ClientType.COMMON);

        when(clientService.findClientById(senderId)).thenReturn(sender);
        when(clientService.findClientById(receiverId)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), senderId, receiverId);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
    }
}