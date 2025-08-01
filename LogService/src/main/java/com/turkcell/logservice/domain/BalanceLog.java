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
@Table(name = "authLog")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceLog {
    @Id
    @Column(name = "log_id")
    private UUID logID;
    @Column(name = "account_id")
    private UUID accountID;
    @Column(name = "balance_block_id")
    private UUID balanceBlockID;
    @Column(name = "ip")
    private String ip;
    @Column(name = "log_time")
    private Instant logTime;

}
