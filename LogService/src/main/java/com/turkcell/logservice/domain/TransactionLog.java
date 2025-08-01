package com.turkcell.logservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactionlog")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLog {
    @Id
    @Column(name = "log_id")
    private UUID logID;
    @Column(name = "transaction_id")
    private UUID transactionId;
    @Column(name = "ip")
    private String ip;
    @Column(name = "log_time")
    private Instant logTime;

}
