package com.turkcell.accountservice.Port;

import com.turkcell.accountservice.Domain.Entities.AccountKYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountKycRepository extends JpaRepository<AccountKYC, UUID>{}