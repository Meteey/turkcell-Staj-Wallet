package com.turkcell.accountservice.Domain.DTO;

import java.time.Instant;
import java.util.UUID;

public record EulaDTO(UUID eulaId, Instant eulaSignedAt) {}
