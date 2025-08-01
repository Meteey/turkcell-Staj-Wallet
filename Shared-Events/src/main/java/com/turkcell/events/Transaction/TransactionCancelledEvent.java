package com.turkcell.events.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionCancelledEvent(UUID transactionId, UUID senderId, UUID receiverId, double amount, String ip, LocalDateTime eventTime) implements ITransaction {

}