package com.turkcell.accountservice.Adapter.in;

import com.turkcell.accountservice.AccountService.AccountService;
import com.turkcell.accountservice.Domain.DTO.RequestDTO.ActivateAccountRequest;
import com.turkcell.accountservice.Domain.DTO.RequestDTO.DeleteAccountRequest;
import com.turkcell.accountservice.Domain.DTO.RequestDTO.UpdateAccountRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/account")
public class RestApiController {

    private final AccountService accountService;

    public RestApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> completeRegistration(
            @RequestBody ActivateAccountRequest request,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            HttpServletRequest http) {
        String ip = getClientIp(http);
        accountService.completeAccountRegistration(request, userId, role, ip);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateOwnAccount(
            @RequestBody UpdateAccountRequest request,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            HttpServletRequest http) {
        String ip = getClientIp(http);
        accountService.updateAccount(request, userId, role, ip);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteOwnAccount(
            @RequestBody DeleteAccountRequest request,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            HttpServletRequest http) {
        String ip = getClientIp(http);
        accountService.deleteAccount(request, userId, role, ip);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account-exists/{accountId}")
    public ResponseEntity<Boolean> accountExists(@PathVariable UUID accountId) {
        boolean exists = accountService.isAccountExists(accountId);
        return ResponseEntity.ok(exists);
    }
    private String getClientIp(HttpServletRequest http) {
        String ip = http.getHeader("X-Forwarded-For");
        return (ip == null || ip.isEmpty()) ? http.getRemoteAddr() : ip;
    }


}
