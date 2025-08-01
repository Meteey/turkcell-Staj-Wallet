package com.turkcell.logservice.port;

import com.turkcell.logservice.domain.AccountLog;
import com.turkcell.logservice.domain.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, UUID> {}
