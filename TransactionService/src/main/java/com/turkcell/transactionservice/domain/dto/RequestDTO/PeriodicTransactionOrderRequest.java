package com.turkcell.transactionservice.domain.dto.RequestDTO;

import com.turkcell.transactionservice.domain.enums.TransactionType;

import java.time.Duration;
import java.util.UUID;

public record PeriodicTransactionOrderRequest(UUID senderId, UUID receiverId, int periodInDays, TransactionType transactionType, double amount) {
}
