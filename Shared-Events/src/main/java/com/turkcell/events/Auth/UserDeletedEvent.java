package com.turkcell.events.Auth;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDeletedEvent(
        UUID userId,
        String ip,
        LocalDateTime deletedAt
) implements IAuth {}
