package com.turkcell.authenticationservice.adapter.out;

import com.turkcell.authenticationservice.adapter.out.persistence.JpaTokenRepository;
import com.turkcell.authenticationservice.domain.models.RefreshToken;
import com.turkcell.authenticationservice.port.TokenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgresTokenAdapter implements TokenRepository {
    private final JpaTokenRepository tokenRepository;

    public PostgresTokenAdapter(JpaTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void save(RefreshToken token) {
        tokenRepository.save(token);
    }




    @Override
    public RefreshToken findByToken(UUID token) {
        return tokenRepository.findById(token).orElse(null);
    }
}
