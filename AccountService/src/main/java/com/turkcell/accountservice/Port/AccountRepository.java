package com.turkcell.accountservice.Port;

import com.turkcell.accountservice.Domain.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {}

