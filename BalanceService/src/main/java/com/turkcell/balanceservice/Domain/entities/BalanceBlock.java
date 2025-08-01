package com.turkcell.balanceservice.Domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceBlock {
    @Id
    @Column(name = "balanceBlockId")
    private UUID balanceBlockId;

    @Column(name = "accountId", nullable = false)
    private UUID accountId;

    @Column(name = "creationDate", nullable = false)
    private Instant creationDate;

    @Column(name = "amount", nullable = false)
    private double amount;
}
