package com.turkcell.balanceservice.Adapter.in;

import com.turkcell.balanceservice.Service.BalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/balance")
public class RestApiController {
    private final BalanceService balanceService;

    public RestApiController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/isBalanceOk/{accountId}")
    ResponseEntity<Boolean> isBalanceEnough(@PathVariable UUID accountId, @RequestBody int amount){
        boolean result = balanceService.isBalanceEnough(accountId, amount);
        return ResponseEntity.ok(result);
    }
}
