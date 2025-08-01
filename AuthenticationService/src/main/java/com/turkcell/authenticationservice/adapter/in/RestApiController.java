package com.turkcell.authenticationservice.adapter.in;

import com.turkcell.authenticationservice.AuthService.AuthService;
import com.turkcell.authenticationservice.AuthService.TokenService;
import com.turkcell.authenticationservice.domain.DTO.RequestContext;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.ChangePasswordRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.LoginRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.RefreshRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.RegisterRequest;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.AuthResponse;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.UserResponse;
import com.turkcell.authenticationservice.domain.models.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class RestApiController {
    private final AuthService authService;
    private final TokenService tokenService;

    public RestApiController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request,
            @CookieValue(value = "refreshToken", required = false) String refreshTokenId,
            HttpServletRequest http,
            HttpServletResponse response) {

        String ip = getClientIp(http);
        Map<String, Object> map = authService.login(request, refreshTokenId, ip);

        AuthResponse authResponse = (AuthResponse) map.get("AuthResponse");
        RefreshToken refreshToken = (RefreshToken) map.get("RefreshToken");
        Cookie cookie = new Cookie("refreshToken", refreshToken.getId().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) Duration.between(Instant.now(), refreshToken.getExpiresAt()).getSeconds());
        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request,
            HttpServletRequest http,
            HttpServletResponse response) {

        String ip = getClientIp(http);
        Map<String, Object> map = authService.register(request, ip);
        AuthResponse authResponse = (AuthResponse) map.get("AuthResponse");

        RefreshToken refreshToken = (RefreshToken) map.get("RefreshToken");
        Cookie cookie = new Cookie("refreshToken", refreshToken.getId().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) Duration.between(Instant.now(), refreshToken.getExpiresAt()).getSeconds());
        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue("refreshToken") String refreshTokenId) {
        RefreshRequest request = new RefreshRequest(refreshTokenId);
        AuthResponse response = tokenService.refreshAccessToken(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logoutUser")
    public ResponseEntity<AuthResponse> logoutUser(@CookieValue("refreshToken") String refreshTokenId, HttpServletRequest http) {
        String ip = getClientIp(http);
        authService.logout(refreshTokenId, ip);
        return ResponseEntity.ok(new AuthResponse(null, "Logged out"));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserResponse> changePassword(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody ChangePasswordRequest request,
            HttpServletRequest http) {
        String token = accessToken.replace("Bearer", "").trim();
        String ip = getClientIp(http);

        RequestContext context = tokenService.extractContextFromAccessToken(token);
        UserResponse response = authService.changePassword(request, context, ip);
        return ResponseEntity.ok(response);
    }


    private String getClientIp(HttpServletRequest http) {
        String ip = http.getHeader("X-Forwarded-For");
        return (ip == null || ip.isEmpty()) ? http.getRemoteAddr() : ip;
    }
}
