package com.turkcell.events.Balance;

import java.time.LocalDateTime;
import java.util.UUID;

public record BalanceIncreasedEvent(UUID accountId, UUID blockId, String ip, LocalDateTime eventTime) implements IBalance {

}