package com.turkcell.events.Balance;

import java.time.LocalDateTime;
import java.util.UUID;

public record BalanceDecreasedEvent(UUID accountId, UUID balanceBlockId, String ip, LocalDateTime eventTime) implements IBalance {

}