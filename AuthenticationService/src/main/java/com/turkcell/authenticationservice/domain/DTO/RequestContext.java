package com.turkcell.authenticationservice.domain.DTO;

import java.util.UUID;

public record RequestContext(UUID userId, String role) {
    public boolean isAdmin() {
        return role != null && role.equalsIgnoreCase("ADMIN");
    }
}
