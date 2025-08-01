package com.turkcell.balanceservice.Service;

import com.turkcell.balanceservice.Domain.entities.BalanceBlock;
import com.turkcell.balanceservice.port.BalanceBlockRepository;
import com.turkcell.events.Balance.BalanceDecreasedEvent;
import com.turkcell.events.Transaction.TransactionCompletedEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BalanceService {
    private final BalanceBlockRepository balanceBlockRepository;
    private final BalanceEventProducer balanceEventProducer;
    public BalanceService(BalanceBlockRepository balanceBlockRepository, BalanceEventProducer balanceEventProducer) {
        this.balanceBlockRepository = balanceBlockRepository;
        this.balanceEventProducer = balanceEventProducer;
    }

    public void handleTransaction(TransactionCompletedEvent transactionCompletedEvent) {
        UUID decreasingBlockId = UUID.randomUUID();
        UUID increasingBlockId = UUID.randomUUID();
        balanceBlockRepository.save(new BalanceBlock(decreasingBlockId, transactionCompletedEvent.senderId(), Instant.now(), transactionCompletedEvent.amount() * -1));
        balanceEventProducer.sendEvent(new BalanceDecreasedEvent(transactionCompletedEvent.senderId(), decreasingBlockId, "EVENT-TRIGGERED", LocalDateTime.now()));
        balanceBlockRepository.save(new BalanceBlock(UUID.randomUUID(), transactionCompletedEvent.receiverId(), Instant.now(), transactionCompletedEvent.amount()));
        balanceEventProducer.sendEvent(new BalanceDecreasedEvent(transactionCompletedEvent.receiverId(), increasingBlockId, "EVENT-TRIGGERED", LocalDateTime.now()));
    }
    public double allBalance(UUID accountID){
        return balanceBlockRepository.getTotalBalanceByAccountId(accountID);
    }
    public boolean isBalanceEnough(UUID accountID, double amount){
        return amount <= allBalance(accountID);
    }
}
