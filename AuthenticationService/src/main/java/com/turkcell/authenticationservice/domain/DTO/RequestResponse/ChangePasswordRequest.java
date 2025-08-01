package com.turkcell.authenticationservice.domain.DTO.RequestResponse;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
