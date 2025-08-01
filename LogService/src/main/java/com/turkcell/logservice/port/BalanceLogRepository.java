package com.turkcell.logservice.port;

import com.turkcell.logservice.domain.AuthLog;
import com.turkcell.logservice.domain.BalanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceLogRepository extends JpaRepository<BalanceLog, UUID> {}
