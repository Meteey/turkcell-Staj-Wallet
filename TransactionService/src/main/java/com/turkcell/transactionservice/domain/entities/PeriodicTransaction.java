package com.turkcell.transactionservice.domain.entities;

import com.turkcell.transactionservice.domain.enums.TransactionStatus;
import com.turkcell.transactionservice.domain.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodicTransaction {
    @Id
    @Column(name = "transaction_id")
    private UUID transactionId;
    @Column(name = "sender_id", nullable = false)
    private UUID senderId;
    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "transactiontype", nullable = false)
    private TransactionType transactionType;
    @Column(name = "periodInDays", nullable = false)
    private int periodInDays;
    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private TransactionStatus status;


}
