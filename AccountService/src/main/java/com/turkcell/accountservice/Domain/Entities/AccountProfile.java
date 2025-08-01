package com.turkcell.accountservice.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "account_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountProfile {
    @Id
    @Column(name = "account_id")
    private UUID accountId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;
    private String name;
    private String surname;
}
