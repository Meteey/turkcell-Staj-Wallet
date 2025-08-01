package com.turkcell.transactionservice.adapter.kafka;

import com.turkcell.events.Balance.BalanceIncreasedEvent;
import com.turkcell.events.Balance.IBalance;
import com.turkcell.transactionservice.Service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener {
    private final TransactionService transactionService;

    public EventListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @KafkaListener(topics = "balance-service-topic", groupId ="transaction-service", containerFactory = "balanceFactory")
    public void listenAuthEvents(IBalance balance){
        if (balance instanceof BalanceIncreasedEvent balanceIncreasedEvent) {
            transactionService.checkWaitingTransactions(balanceIncreasedEvent);
        }
    }
}
