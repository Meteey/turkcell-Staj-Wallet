package com.turkcell.transactionservice.adapter.in;

import com.turkcell.transactionservice.Service.TransactionService;
import com.turkcell.transactionservice.domain.dto.RequestDTO.PeriodicTransactionOrderRequest;
import com.turkcell.transactionservice.domain.dto.RequestDTO.TransactionRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class RestApiController {
    private final TransactionService transactionService;

    public RestApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/initiateTransaction")
    public ResponseEntity<Void> initiateTransaction(@RequestBody TransactionRequestDTO transactionRequest, HttpServletRequest http) {
        String ip = getClientIp(http);
        transactionService.initiateTransaction(transactionRequest, ip);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/undoTransaction/{transactionId}")
    public ResponseEntity<Void> undoTransaction(
            @PathVariable UUID transactionId,
            HttpServletRequest http
    ) {
        String role = http.getHeader("X-Role");
        String ip = getClientIp(http);
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        transactionService.undoTransaction(transactionId, ip);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/periodicOrder")
    public ResponseEntity<Void> periodicTransactionOrder(
            @RequestBody PeriodicTransactionOrderRequest request,
            HttpServletRequest http
    ) {
        String ip = getClientIp(http);
        transactionService.periodicTransactionOrder(request, ip);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancelPeriodicOrder/{transactionId}")
    public ResponseEntity<Void> cancelPeriodicOrder(
            @PathVariable UUID transactionId,
            HttpServletRequest http
    ) {
        String ip = getClientIp(http);
        transactionService.cancelPeriodicTransactionOrder(transactionId, ip);
        return ResponseEntity.ok().build();
    }

    private String getClientIp(HttpServletRequest http) {
        String ip = http.getHeader("X-Forwarded-For");
        return (ip == null || ip.isEmpty()) ? http.getRemoteAddr() : ip;
    }
}
