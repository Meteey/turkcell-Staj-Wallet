package com.turkcell.accountservice.Domain.DTO.RequestDTO;

import java.util.UUID;

public record DeleteAccountRequest(UUID accountId) {
}
