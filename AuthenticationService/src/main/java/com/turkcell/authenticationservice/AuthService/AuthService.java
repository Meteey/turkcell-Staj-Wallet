package com.turkcell.authenticationservice.AuthService;

import com.turkcell.authenticationservice.domain.DTO.RequestContext;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.ChangePasswordRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.LoginRequest;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.RegisterRequest;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.AuthResponse;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.UserResponse;
import com.turkcell.authenticationservice.domain.Exceptions.ConflictException;
import com.turkcell.authenticationservice.domain.Exceptions.NotFoundException;
import com.turkcell.authenticationservice.domain.Exceptions.UnauthorizedException;
import com.turkcell.authenticationservice.domain.models.RefreshToken;
import com.turkcell.authenticationservice.domain.models.User;
import com.turkcell.authenticationservice.domain.models.UserRoles;
import com.turkcell.authenticationservice.port.IAuthService;
import com.turkcell.authenticationservice.port.TokenRepository;
import com.turkcell.authenticationservice.port.UserRepository;
import com.turkcell.events.Auth.*;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {
    private final AuthEventProducer authEventProducer;
    private final UserRepository userAdapter;
    private final TokenRepository tokenAdapter;
    private final TokenService tokenService;

    public AuthService(AuthEventProducer authEventProducer, UserRepository userAdapter, TokenRepository tokenAdapter, TokenService tokenService) {
        this.authEventProducer = authEventProducer;
        this.userAdapter = userAdapter;
        this.tokenAdapter = tokenAdapter;
        this.tokenService = tokenService;
    }
    @Override
    public Map<String, Object> login(LoginRequest request, String ip, String refreshTokenId) {
        // Refresh token geçerli ise bypass
        if (refreshTokenId != null) {
            try {
                RefreshToken token = tokenAdapter.findByToken(UUID.fromString(refreshTokenId));
                if (tokenService.isRefreshTokenValid(token)) {
                    User user = userAdapter.findById(token.getUserId());
                    String jwt = tokenService.generateAccessToken(user);
                    Map<String,Object> returnmap = new HashMap<>();
                    returnmap.put("AuthResponse" , new AuthResponse(jwt, "Already logged in (via refresh token)"));
                    returnmap.put("RefreshToken", token);
                    return returnmap;
                }
            } catch (Exception ignored) {
                // geçersiz token varsa bypass etme
            }
        }

        // Normal login akışı
        User user = userAdapter.findByEmail(request.mail());
        if (user == null) {
            throw new NotFoundException("User not found with mail: " + request.mail());
        }

        if (!Util.checkHashedText(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Wrong Password");
        }

        user.setActive(true);
        user.setUpdatedAt(Instant.now());
        userAdapter.save(user);

        String jwt = tokenService.generateAccessToken(user);
        RefreshToken newToken = tokenService.generateRefreshToken(user);
        tokenAdapter.save(newToken);

        authEventProducer.sendEvent(new UserLoggedInEvent(user.getId(), ip, LocalDateTime.now()));
        Map<String,Object> returnmap = new HashMap<>();
        returnmap.put("AuthResponse" , new AuthResponse(jwt, "Logged-in successfully"));
        returnmap.put("RefreshToken", newToken);
        return returnmap;
    }

    @Override
    public Map<String, Object> register(RegisterRequest request, String ip) {

        User user = userAdapter.findByEmail(request.mail());
        if(user == null){
            User newUser = new User();
            newUser.setEmail(request.mail());
            String hashedPassword = Util.hashText(request.password());
            newUser.setPassword(hashedPassword);
            newUser.setId(UUID.randomUUID());
            newUser.setCreatedAt(Instant.now());
            newUser.setUpdatedAt(Instant.now());
            newUser.setActive(true);
            newUser.setRole(UserRoles.USER.toString());
            userAdapter.save(newUser);
            RefreshToken token = tokenService.generateRefreshToken(newUser);
            tokenAdapter.save(token);
            String jwt = tokenService.generateAccessToken(newUser);
            authEventProducer.sendEvent(new UserCreatedEvent(newUser.getId(), ip, LocalDateTime.now()));
            Map<String,Object> returnmap = new HashMap<>();
            returnmap.put("AuthResponse" , new AuthResponse(jwt, "Registered successfully"));
            returnmap.put("RefreshToken", token);
            return returnmap;
        }
        else{
            throw new ConflictException("This mail already exists");
        }
    }

    @Override
    public void logout(String refreshToken, String ip) {
        RefreshToken token = tokenAdapter.findByToken(UUID.fromString(refreshToken));

        User user = userAdapter.findById(token.getUserId());
        user.setActive(false);
        user.setUpdatedAt(Instant.now());
        userAdapter.save(user);
        tokenService.revokeToken(token.getId());
        tokenAdapter.save(token);
        authEventProducer.sendEvent(new UserLoggedOutEvent(user.getId(), ip, LocalDateTime.now()));

    }


    @Override
    public UserResponse changePassword(ChangePasswordRequest request, RequestContext context, String ip) {
        User user = userAdapter.findById(context.userId());
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (context.isAdmin() || Util.checkHashedText(request.oldPassword(), user.getPassword())) {
            user.setPassword(Util.hashText(request.newPassword()));
            user.setUpdatedAt(Instant.now());
            userAdapter.save(user);

            authEventProducer.sendEvent(new UserChangedPasswordEvent(user.getId(), ip, LocalDateTime.now()));
            return new UserResponse("Successfully changed");
        } else {
            throw new ConflictException("Wrong old password");
        }
    }


    @Override
    public void deleteUser(UUID uid, String ip) {
        User user = userAdapter.findById(uid);
        if(user != null){
            userAdapter.delete(user);
            authEventProducer.sendEvent(new UserDeletedEvent(user.getId(), ip, LocalDateTime.now()));
        }else{
            throw new NotFoundException("User already deleted");
        }

    }
}
