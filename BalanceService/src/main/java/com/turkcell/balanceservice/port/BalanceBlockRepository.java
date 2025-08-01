package com.turkcell.balanceservice.port;
import com.turkcell.balanceservice.Domain.entities.BalanceBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface BalanceBlockRepository extends JpaRepository<BalanceBlock, UUID> {
    Optional<BalanceBlock> findBalanceBlockByBalanceBlockId(UUID balanceBlockId);
    @Query("SELECT SUM(b.amount) FROM BalanceBlock b WHERE B.accountId = :accountId ")
    double getTotalBalanceByAccountId(UUID accountId);

}
