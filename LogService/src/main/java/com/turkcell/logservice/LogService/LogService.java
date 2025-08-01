package com.turkcell.logservice.LogService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.turkcell.events.Account.*;
import com.turkcell.events.Auth.*;
import com.turkcell.events.Balance.BalanceDecreasedEvent;
import com.turkcell.events.Balance.BalanceIncreasedEvent;
import com.turkcell.events.Balance.IBalance;
import com.turkcell.events.Transaction.ITransaction;
import com.turkcell.events.Transaction.TransactionCancelledEvent;
import com.turkcell.events.Transaction.TransactionCompletedEvent;
import com.turkcell.logservice.domain.*;
import com.turkcell.logservice.port.AccountLogRepository;
import com.turkcell.logservice.port.AuthLogRepository;
import com.turkcell.logservice.port.BalanceLogRepository;
import com.turkcell.logservice.port.TransactionLogRepository;
import org.aspectj.weaver.IEclipseSourceContext;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class LogService {
    private final AccountLogRepository accountLogRepository;
    private final BalanceLogRepository balanceLogRepository;
    private final AuthLogRepository authLogRepository;
    private final TransactionLogRepository transactionLogRepository;

    public LogService(AccountLogRepository accountLogRepository, BalanceLogRepository balanceLogRepository, AuthLogRepository authLogRepository, TransactionLogRepository transactionLogRepository) {
        this.accountLogRepository = accountLogRepository;
        this.balanceLogRepository = balanceLogRepository;
        this.authLogRepository = authLogRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    public void handleAccountLog(IAccount accountEvent){
        UUID logId = UUID.randomUUID();
        AccountLog accountLog = null;
        switch (accountEvent) {
            case AccountPendingEvent event -> accountLog = new AccountLog(logId, event.accountId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case AccountActivatedEvent event -> accountLog = new AccountLog(logId, event.accountId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case AccountDeletedEvent event -> accountLog = new AccountLog(logId, event.accountId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case AccountUpdatedEvent event -> accountLog = new AccountLog(logId, event.accountId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case null, default -> System.out.println("[ERR] Event tanınamadı");
        }
        if(accountLog != null){
            accountLogRepository.save(accountLog);
        }
    }

    public void handleAuthLog(IAuth authEvent) {
        UUID logid = UUID.randomUUID();
        AuthLog authLog = null;
        switch (authEvent){
            case UserChangedPasswordEvent event -> authLog = new AuthLog(logid, event.userId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case UserLoggedInEvent event -> authLog = new AuthLog(logid, event.userId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case UserCreatedEvent event -> authLog = new AuthLog(logid, event.userId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case UserDeletedEvent event -> authLog = new AuthLog(logid, event.userId(), event.ip(), event.deletedAt().toInstant(ZoneOffset.UTC));
            case UserLoggedOutEvent event -> authLog = new AuthLog(logid, event.userId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case null, default -> System.out.println("[ERR] Event tanınamadı");
        }
        if(authLog != null){
            authLogRepository.save(authLog);
        }
    }
    public void handleBalanceLog(IBalance balanceEvent){
        UUID logid = UUID.randomUUID();
        BalanceLog balanceLog = null;
        switch (balanceEvent){
            case BalanceIncreasedEvent event -> balanceLog = new BalanceLog(logid, event.accountId(), event.blockId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case BalanceDecreasedEvent event -> balanceLog = new BalanceLog(logid, event.accountId(), event.balanceBlockId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case null, default -> System.out.println("[ERR] Event tanınamadı");
        }
        if (balanceLog != null){
            balanceLogRepository.save(balanceLog);
        }
    }
    public void handleTransactionLog(ITransaction transactionEvent){
        UUID logid = UUID.randomUUID();
        TransactionLog transactionLog = null;
        switch (transactionEvent){
            case TransactionCancelledEvent event-> transactionLog = new TransactionLog(logid,event.transactionId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case TransactionCompletedEvent event-> transactionLog = new TransactionLog(logid,event.transactionId(), event.ip(), event.eventTime().toInstant(ZoneOffset.UTC));
            case null, default -> System.out.println("[ERR] Event tanınamadı");
        }
        if (transactionLog!=null){
            transactionLogRepository.save(transactionLog);
        }
    }
}
