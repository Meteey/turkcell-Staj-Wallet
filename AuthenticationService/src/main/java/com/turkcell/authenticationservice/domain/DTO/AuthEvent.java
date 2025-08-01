package com.turkcell.authenticationservice.domain.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public record AuthEvent(Map<String, String> message, String service, LocalDateTime logTime, String ipAddr, String level) {
}
