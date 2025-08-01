package com.turkcell.balanceservice.Adapter.kafka;

import com.turkcell.balanceservice.Service.BalanceService;
import com.turkcell.events.Transaction.ITransaction;
import com.turkcell.events.Transaction.TransactionCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener {
    private final BalanceService balanceService;

    public EventListener(BalanceService balanceService) {
        this.balanceService = balanceService;
    }
    @KafkaListener(topics = "transaction-service-topic", groupId = "balance-service", containerFactory = "transactionFactory")
    public void listenTransactionEvents(ITransaction transactionEvent){
        if(transactionEvent instanceof TransactionCompletedEvent transactionCompletedEvent){
            balanceService.handleTransaction(transactionCompletedEvent);
        }
    }
}
