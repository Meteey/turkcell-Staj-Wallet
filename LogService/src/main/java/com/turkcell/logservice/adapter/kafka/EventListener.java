package com.turkcell.logservice.adapter.kafka;

import com.turkcell.events.Account.*;
import com.turkcell.events.Auth.IAuth;
import com.turkcell.events.Auth.UserCreatedEvent;
import com.turkcell.events.Auth.UserDeletedEvent;
import com.turkcell.events.Auth.UserChangedPasswordEvent;
import com.turkcell.events.Auth.UserLoggedOutEvent;
import com.turkcell.events.Auth.UserLoggedInEvent;
import com.turkcell.events.Balance.BalanceDecreasedEvent;
import com.turkcell.events.Balance.BalanceIncreasedEvent;
import com.turkcell.events.Balance.IBalance;
import com.turkcell.events.Transaction.ITransaction;
import com.turkcell.events.Transaction.TransactionCancelledEvent;
import com.turkcell.events.Transaction.TransactionCompletedEvent;
import com.turkcell.logservice.LogService.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//TODO : her service için ayrı handler ve log tablosu
@Slf4j
@Service
public class EventListener {
    private final LogService logService;
    public EventListener(LogService logService){
        this.logService = logService;
    }
    @KafkaListener(topics="auth-service-topic", groupId = "log-service",containerFactory = "authFactory")
    public void listenAuthEvents(IAuth authEvent){
        logService.handleAuthLog(authEvent);


    }
    @KafkaListener(topics = "account-service-topic", groupId = "log-service", containerFactory = "accountFactory")
    public void listenAccountEvents(IAccount accountEvent){
        logService.handleAccountLog(accountEvent);
    }
    @KafkaListener(topics = "transaction-service-topic", groupId = "log-service", containerFactory = "transactionFactory")
    public void listenTransactionEvents(ITransaction transactionEvent){
        if(transactionEvent instanceof TransactionCompletedEvent event){
            System.out.println(event.transactionId() + " " + "nolu transaction gerçekleştirildi");
        }
        else if(transactionEvent instanceof TransactionCancelledEvent event){
            System.out.println(event.transactionId() + " " + " nolu transaction gerçekleştirilemedi.");
        }
        else {
            System.out.println("[ERR] Event tanınamadı");
        }
    }
    @KafkaListener(topics = "balance-service-topic", groupId = "log-service", containerFactory = "balanceFactory")
    public void listenBalanceEvent(IBalance balanceEvent){
        logService.handleBalanceLog(balanceEvent);
    }


}
