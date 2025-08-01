package com.turkcell.logservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "accountLog")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLog {
    @Id
    @Column(name = "log_id")
    private UUID logID;
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "ip")
    private String ip;
    @Column(name = "log_time")
    private Instant logTime;

}
