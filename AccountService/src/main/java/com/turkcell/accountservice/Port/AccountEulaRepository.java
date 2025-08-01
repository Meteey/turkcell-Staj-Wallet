package com.turkcell.accountservice.Port;

import com.turkcell.accountservice.Domain.Entities.AccountEula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountEulaRepository extends JpaRepository<AccountEula, UUID> {
}