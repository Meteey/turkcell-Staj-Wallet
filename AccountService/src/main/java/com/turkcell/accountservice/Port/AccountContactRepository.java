package com.turkcell.accountservice.Port;

import com.turkcell.accountservice.Domain.Entities.AccountContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountContactRepository extends JpaRepository<AccountContact, UUID> {}

