package com.turkcell.logservice.port;

import com.turkcell.logservice.domain.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountLogRepository extends JpaRepository<AccountLog, UUID> {}
