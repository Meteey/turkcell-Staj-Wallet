package com.turkcell.events.Auth;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserChangedPasswordEvent(UUID userId,
                                       String ip,
                                       LocalDateTime eventTime) implements IAuth {
}
