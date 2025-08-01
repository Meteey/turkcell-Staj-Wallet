package com.turkcell.authenticationservice.port;

import com.turkcell.authenticationservice.domain.DTO.RequestContext;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.ChangePasswordRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.LoginRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.RegisterRequest;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.AuthResponse;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.UserResponse;

import java.util.Map;
import java.util.UUID;


public interface IAuthService {
    Map<String, Object> login(LoginRequest request, String ip, String tokenId);
    Map<String, Object> register(RegisterRequest request, String ip);
    void logout(String refreshToken, String ip);

    UserResponse changePassword(ChangePasswordRequest request, RequestContext context, String ip);

    void deleteUser(UUID uid, String ip);


}
