package com.turkcell.accountservice.Domain.DTO.RequestDTO;

import com.turkcell.accountservice.Domain.DTO.ContactDTO;
import com.turkcell.accountservice.Domain.DTO.EulaDTO;
import com.turkcell.accountservice.Domain.DTO.KycDTO;
import com.turkcell.accountservice.Domain.DTO.ProfileDTO;

import java.util.UUID;

public record ActivateAccountRequest(
        UUID accountId,
        ProfileDTO profile,
        ContactDTO contact,
        KycDTO kyc,
        EulaDTO eula
) {}
