package com.turkcell.authenticationservice.port;

import com.turkcell.authenticationservice.domain.models.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {
    // token table
    void save(RefreshToken token);
    RefreshToken findByToken(UUID token);
}

