package com.turkcell.transactionservice.Service;

import com.turkcell.events.Balance.BalanceIncreasedEvent;
import com.turkcell.events.Transaction.PeriodicTransactionAppliedEvent;
import com.turkcell.events.Transaction.PeriodicTransactionCancelledEvent;
import com.turkcell.events.Transaction.TransactionCancelledEvent;
import com.turkcell.events.Transaction.TransactionCompletedEvent;
import com.turkcell.transactionservice.Port.PeriodicTransactionRepository;
import com.turkcell.transactionservice.Port.TransactionRepository;
import com.turkcell.transactionservice.domain.dto.RequestDTO.PeriodicTransactionOrderRequest;
import com.turkcell.transactionservice.domain.dto.RequestDTO.TransactionRequestDTO;
import com.turkcell.transactionservice.domain.entities.PeriodicTransaction;
import com.turkcell.transactionservice.domain.entities.Transaction;
import com.turkcell.transactionservice.domain.enums.TransactionStatus;
import com.turkcell.transactionservice.domain.enums.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionEventProducer transactionEventProducer;
    private final PeriodicTransactionRepository periodicTransactionRepository;
    public TransactionService(TransactionRepository transactionRepository, TransactionEventProducer transactionEventProducer, PeriodicTransactionRepository periodicTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionEventProducer = transactionEventProducer;
        this.periodicTransactionRepository = periodicTransactionRepository;
    }

    public void initiateTransaction(TransactionRequestDTO transactionRequest, String ip) {
        if(checkAccountExistence(transactionRequest.senderUUID()) && checkAccountExistence(transactionRequest.receiverUUID())){
            if(checkBalanceRestrictions(transactionRequest.senderUUID())){
                if (checkAccountBlockage(transactionRequest.senderUUID(), transactionRequest.transactionType())){
                    UUID transactionId = UUID.randomUUID();
                    Transaction transaction = new Transaction(transactionId, transactionRequest.senderUUID(),transactionRequest.receiverUUID(), transactionRequest.amount(), transactionRequest.transactionType(), Instant.now(), TransactionStatus.APPLIED);
                    transactionRepository.save(transaction);
                    transactionEventProducer.sendEvent(new TransactionCompletedEvent(transaction.getTransactionId(), transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount(), ip, LocalDateTime.now()));
                }
                // hesabın blokajı var. (bekleyen işlem)
            } else if (transactionRequest.transactionType() == TransactionType.PARA_IADE) {
                // bakiye yetersiz ama undo transaction işlemi girilmiş.
                UUID transactionId = UUID.randomUUID();
                Transaction transaction = new Transaction(transactionId, transactionRequest.senderUUID(),transactionRequest.receiverUUID(), transactionRequest.amount(), transactionRequest.transactionType(), Instant.now(), TransactionStatus.WAITING);
                transactionRepository.save(transaction);
                transactionEventProducer.sendEvent(new TransactionCompletedEvent(transaction.getTransactionId(), transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount(), ip, LocalDateTime.now()));

            }
        }
    }
    public void undoTransaction(UUID transactionId, String ip){
        Optional<Transaction> optionalTransaction = transactionRepository.findTransactionByTransactionId(transactionId);
        if (optionalTransaction.isPresent()){
            Transaction transaction = optionalTransaction.get();
            if (transaction.getStatus() == TransactionStatus.CANCELLED) return;
            transaction.setStatus(TransactionStatus.CANCELLED);
            transactionRepository.save(transaction);
            initiateTransaction(new TransactionRequestDTO(transaction.getReceiverId(), transaction.getSenderId(), TransactionType.PARA_IADE, transaction.getAmount()), "INTERNALLY TRIGGERED");
            transactionEventProducer.sendEvent(new TransactionCancelledEvent(transaction.getTransactionId(), transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount(), ip, LocalDateTime.now()));

        }
    }
    public void periodicTransactionOrder(PeriodicTransactionOrderRequest request, String ip){
        if(checkAccountExistence(request.senderId()) && checkAccountExistence(request.receiverId())){
            UUID periodicTransactionId = UUID.randomUUID();
            PeriodicTransaction pTransaction = new PeriodicTransaction(periodicTransactionId, request.senderId(), request.receiverId(), request.amount(), request.transactionType(), request.periodInDays(), TransactionStatus.APPLIED);
            periodicTransactionRepository.save(pTransaction);
            transactionEventProducer.sendEvent(new PeriodicTransactionAppliedEvent(pTransaction.getTransactionId(), ip, LocalDateTime.now()));
        }
    }
    public void cancelPeriodicTransactionOrder(UUID transactionId, String ip){
        Optional<PeriodicTransaction> optionalPeriodicTransaction = periodicTransactionRepository.findTransactionByTransactionId(transactionId);
        if (optionalPeriodicTransaction.isPresent()){
            PeriodicTransaction transaction = optionalPeriodicTransaction.get();
            transaction.setStatus(TransactionStatus.CANCELLED);
            periodicTransactionRepository.save(transaction);
            transactionEventProducer.sendEvent(new PeriodicTransactionCancelledEvent(transactionId, ip, LocalDateTime.now()));
        }
    }

    private boolean checkAccountExistence(UUID accountId){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        Boolean result = webClient.get()
                .uri("/account/account-exists/" + accountId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(result);
    }
    private boolean checkBalanceRestrictions(UUID senderId){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        Boolean result = webClient.get()
                .uri("/balance/isBalanceOk/" + senderId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(result);

    }
    private boolean checkAccountBlockage(UUID senderId, TransactionType transactionType){
        if (transactionType == TransactionType.VIRMAN){
            return true;
        }
        List<Transaction> waitingTransactions =  transactionRepository.findBySenderIdAndStatus(senderId, TransactionStatus.WAITING);
        return waitingTransactions.isEmpty();
    }

    public void checkWaitingTransactions(BalanceIncreasedEvent balanceIncreasedEvent) {
        List<Transaction> waitingTransactions =  transactionRepository.findByTransactionIdAndStatus(balanceIncreasedEvent.accountId(), TransactionStatus.WAITING);
        for(Transaction transaction:waitingTransactions){
            initiateTransaction(new TransactionRequestDTO(transaction.getSenderId(), transaction.getReceiverId(), TransactionType.PARA_IADE, transaction.getAmount()), "EVENT-TRIGGERED");
        }

    }
}
