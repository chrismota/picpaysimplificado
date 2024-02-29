package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.Client;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AuthTransactionService authService;

    @Autowired
    private NotificationService notificationService;
    public Transaction createTransaction(TransactionDTO transactionDto) throws Exception {
        Client sender = this.clientService.findClientById(transactionDto.senderId());
        Client receiver = this.clientService.findClientById(transactionDto.receiverId());

        clientService.validateTransaction(sender, transactionDto.value());

        boolean isAuthorized = this.authService.authorizeTransaction(sender, transactionDto.value());

        if (!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDto.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDto.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.value()));

        this.repository.save(newTransaction);
        this.clientService.saveClient(sender);
        this.clientService.saveClient(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso.");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso.");

        return newTransaction;
    }

}
