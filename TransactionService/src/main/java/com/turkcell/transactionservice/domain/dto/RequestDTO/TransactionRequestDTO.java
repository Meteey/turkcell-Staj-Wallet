package com.turkcell.transactionservice.domain.dto.RequestDTO;

import com.turkcell.transactionservice.domain.enums.TransactionType;

import java.util.UUID;

public record TransactionRequestDTO(UUID senderUUID, UUID receiverUUID, TransactionType transactionType, double amount) {
}
