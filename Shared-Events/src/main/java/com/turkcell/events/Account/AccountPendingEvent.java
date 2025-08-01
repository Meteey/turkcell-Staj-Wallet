package com.turkcell.events.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountPendingEvent(UUID accountId, UUID userId, String ip, LocalDateTime eventTime) implements IAccount {
}
