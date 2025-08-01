package com.turkcell.authenticationservice.domain.DTO.ResponseDTO;


import org.springframework.lang.Nullable;

public record AuthResponse(@Nullable String token, String message){}
