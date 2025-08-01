package com.turkcell.accountservice.Domain.DTO.RequestDTO;

import com.turkcell.accountservice.Domain.DTO.ContactDTO;
import com.turkcell.accountservice.Domain.DTO.KycDTO;
import com.turkcell.accountservice.Domain.DTO.ProfileDTO;

import java.util.UUID;

public record UpdateAccountRequest(
        UUID accountId,
        ContactDTO contact,
        ProfileDTO profile,
        KycDTO kyc
) {
    public UpdateAccountRequest withAccountId(UUID accountId) {
        return new UpdateAccountRequest(accountId, this.contact, this.profile, this.kyc);
    }
}
