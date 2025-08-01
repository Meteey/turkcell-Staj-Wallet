package com.turkcell.transactionservice.Port;

import com.turkcell.transactionservice.domain.entities.Transaction;
import com.turkcell.transactionservice.domain.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findTransactionByTransactionId(UUID transactionId);
    List<Transaction> findByTransactionIdAndStatus(UUID transactionId, TransactionStatus status);
    List<Transaction> findBySenderIdAndStatus(UUID senderId, TransactionStatus status);

}
