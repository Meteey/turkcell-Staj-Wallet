package com.turkcell.accountservice.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account_contact")
public class AccountContact {
    @Id
    @Column(name = "account_id")
    private UUID account_id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;
    private String msisdn;
    private String email;
}
