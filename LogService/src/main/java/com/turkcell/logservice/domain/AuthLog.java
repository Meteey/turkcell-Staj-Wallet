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
public class AuthLog {
    @Id
    @Column(name = "log_id")
    private UUID logID;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "ip")
    private String ip;
    @Column(name = "log_time")
    private Instant logTime;

}
