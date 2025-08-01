package com.turkcell.accountservice.Port;

import com.turkcell.accountservice.Domain.Entities.Account;
import com.turkcell.accountservice.Domain.Entities.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, UUID>{

}