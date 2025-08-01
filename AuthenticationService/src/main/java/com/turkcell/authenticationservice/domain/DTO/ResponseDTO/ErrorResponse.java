package com.turkcell.authenticationservice.domain.DTO.ResponseDTO;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {
}
