package com.turkcell.authenticationservice.adapter.out;

import com.turkcell.authenticationservice.adapter.out.persistence.JpaTokenRepository;
import com.turkcell.authenticationservice.adapter.out.persistence.JpaUserRepository;
import com.turkcell.authenticationservice.domain.models.RefreshToken;
import com.turkcell.authenticationservice.domain.models.User;
import com.turkcell.authenticationservice.port.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgresUserAdapter implements UserRepository {

    private final JpaUserRepository userRepository;

    public PostgresUserAdapter(JpaUserRepository userRepository, JpaTokenRepository tokenRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
