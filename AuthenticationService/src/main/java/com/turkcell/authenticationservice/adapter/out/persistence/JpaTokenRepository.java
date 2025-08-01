package com.turkcell.authenticationservice.adapter.out.persistence;

import com.turkcell.authenticationservice.domain.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTokenRepository extends JpaRepository<RefreshToken, UUID> {

    void deleteUserById(UUID userId);
}
