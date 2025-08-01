package com.turkcell.accountservice.AccountService;

import com.turkcell.accountservice.Domain.DTO.RequestDTO.ActivateAccountRequest;
import com.turkcell.accountservice.Domain.DTO.RequestDTO.DeleteAccountRequest;
import com.turkcell.accountservice.Domain.DTO.RequestDTO.UpdateAccountRequest;
import com.turkcell.accountservice.Domain.Entities.*;
import com.turkcell.accountservice.Port.*;
import com.turkcell.events.Account.AccountActivatedEvent;
import com.turkcell.events.Account.AccountDeletedEvent;
import com.turkcell.events.Account.AccountPendingEvent;
import com.turkcell.events.Account.AccountUpdatedEvent;
import com.turkcell.events.Auth.UserCreatedEvent;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountProfileRepository profileRepository;
    private final AccountKycRepository kycRepository;
    private final AccountContactRepository contactRepository;
    private final AccountEulaRepository eulaRepository;
    private final AccountEventProducer accountEventProducer;
    public AccountService(AccountRepository accountRepository, AccountProfileRepository profileRepository, AccountKycRepository kycRepository, AccountContactRepository contactRepository, AccountEulaRepository eulaRepository, AccountEventProducer accountEventProducer) {
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
        this.kycRepository = kycRepository;
        this.contactRepository = contactRepository;
        this.eulaRepository = eulaRepository;
        this.accountEventProducer = accountEventProducer;
    }
    @Transactional
    public void deleteAccount(DeleteAccountRequest request, String ip) {
        UUID accountId = request.accountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        accountRepository.delete(account);

        accountEventProducer.sendEvent(new AccountDeletedEvent(
                accountId,
                account.getUserId(),
                ip,
                LocalDateTime.now()
        ));
    }
    @Transactional
    public void updateAccount(UpdateAccountRequest req, UUID callerUserId, String role, String ip) {
        UUID accountId = req.accountId();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!callerUserId.equals(account.getUserId()) && !role.equalsIgnoreCase("ADMIN")) {
            throw new SecurityException("Unauthorized operation");
        }
        // 1. contact
        AccountContact contact = contactRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Contact not found"));

        contact.setEmail(req.contact().email());
        contact.setMsisdn(req.contact().msisdn());
        contactRepository.save(contact);

        // 2. profile
        AccountProfile profile = profileRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Profile not found"));

        profile.setName(req.profile().name());
        profile.setSurname(req.profile().surname());
        profileRepository.save(profile);

        // 3. kyc (isteğe bağlı)
        AccountKYC kyc = kycRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("KYC not found"));

        kyc.setNationalId(req.kyc().nationalId());
        kyc.setNationalIdType(req.kyc().nationalIdType());
        kyc.setKycType(req.kyc().kycType());
        kyc.setExceptionalDesc(req.kyc().exceptionalDesc());
        kyc.setIsExceptional(req.kyc().isExceptional());
        kycRepository.save(kyc);

        // 4. update timestamp
        account.setUpdatedAt(Instant.now());
        accountRepository.save(account);

        // 5. event yayınla (isteğe bağlı)
        accountEventProducer.sendEvent(new AccountUpdatedEvent(
                accountId,
                account.getUserId(),
                ip,
                LocalDateTime.now()
        ));
    }



    public void createPendingAccountFromEvent(UserCreatedEvent event) {
        UUID accountId = UUID.randomUUID();
        Instant now = Instant.now();
        Account account = new Account(accountId, event.userId(), "PENDING", now, now);
        accountRepository.save(account);
        accountEventProducer.sendEvent(new AccountPendingEvent(account.getAccountId(), account.getUserId(), "EVENT-TRIGGERED", LocalDateTime.now()));
    }
    @Transactional
    public void completeAccountRegistration(ActivateAccountRequest req, UUID callerUserId, String role, String ip) {
        Account account = accountRepository.findById(req.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!callerUserId.equals(account.getUserId()) && !role.equalsIgnoreCase("ADMIN")) {
            throw new SecurityException("Unauthorized operation");
        }

        profileRepository.save(AccountProfile.builder()
                .account(account)
                .name(req.profile().name())
                .surname(req.profile().surname())
                .build()
        );
        contactRepository.save(AccountContact.builder()
                .account(account)
                .email(req.contact().email())
                .msisdn(req.contact().msisdn())
                .build()
        );
        eulaRepository.save(AccountEula.builder()
                .account(account)
                .eulaId(req.eula().eulaId())
                .eulaSignedAt(req.eula().eulaSignedAt())
                .build()
        );
        kycRepository.save(AccountKYC.builder()
                .account(account)
                .kycType(req.kyc().kycType())
                .nationalId(req.kyc().nationalId())
                .nationalIdType(req.kyc().nationalIdType())
                .exceptionalDesc(req.kyc().exceptionalDesc())
                .isExceptional(req.kyc().isExceptional())
                .build()
        );
        account.setStatus("ACTIVE");
        account.setUpdatedAt(Instant.now());
        accountRepository.save(account);
        accountEventProducer.sendEvent(new AccountActivatedEvent(account.getAccountId(), account.getUserId(), ip, LocalDateTime.now()));


    }

    public boolean isAccountExists(UUID accountId) {
        return accountRepository.existsById(accountId);
    }
}
