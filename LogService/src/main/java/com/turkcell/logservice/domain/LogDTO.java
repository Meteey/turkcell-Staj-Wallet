package com.turkcell.logservice.domain;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Map;

public record LogDTO(Map<String, String> message, String service, LocalDateTime logTime, String ipAddr, String level) {
}
