package com.turkcell.accountservice.Domain.DTO;

public record KycDTO(String nationalId, String nationalIdType, String kycType, boolean isExceptional, String exceptionalDesc) {}
