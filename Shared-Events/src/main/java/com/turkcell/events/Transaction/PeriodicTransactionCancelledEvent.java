package com.turkcell.events.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public record PeriodicTransactionCancelledEvent(UUID transactionId, String ip, LocalDateTime eventTime) implements ITransaction {

}