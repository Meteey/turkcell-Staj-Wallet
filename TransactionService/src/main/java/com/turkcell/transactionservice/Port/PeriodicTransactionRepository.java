package com.turkcell.transactionservice.Port;
import com.turkcell.transactionservice.domain.entities.PeriodicTransaction;
import com.turkcell.transactionservice.domain.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodicTransactionRepository extends JpaRepository<PeriodicTransaction, UUID> {


    Optional<PeriodicTransaction> findTransactionByTransactionId(UUID transactionId);
    List<PeriodicTransaction> findByTransactionIdAndStatus(UUID transactionId, TransactionStatus status);



}
