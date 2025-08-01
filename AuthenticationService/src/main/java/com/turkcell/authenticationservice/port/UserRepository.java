package com.turkcell.authenticationservice.port;

import com.turkcell.authenticationservice.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    void delete(User user);
    User findByEmail(String email);
    User findById(UUID id);
}