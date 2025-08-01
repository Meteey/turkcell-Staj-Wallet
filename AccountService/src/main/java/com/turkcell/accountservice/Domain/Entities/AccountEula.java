package com.turkcell.accountservice.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "account_eula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEula {
    @Id
    @Column(name = "account_id")
    private UUID accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    private UUID eulaId;

    @Column(name = "eula_signed_at")
    private Instant eulaSignedAt;

    // Getters, setters, constructor
}
