package com.turkcell.logservice.port;

import com.turkcell.logservice.domain.AuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthLogRepository extends JpaRepository<AuthLog, UUID> {}
