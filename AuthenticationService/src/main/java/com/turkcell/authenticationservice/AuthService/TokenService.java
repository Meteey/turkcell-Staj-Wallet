package com.turkcell.authenticationservice.AuthService;

import com.turkcell.authenticationservice.config.JwtConfiguration;
import com.turkcell.authenticationservice.domain.DTO.RequestContext;
import com.turkcell.authenticationservice.domain.DTO.ResponseDTO.AuthResponse;
import com.turkcell.authenticationservice.domain.DTO.RequestResponse.RefreshRequest;
import com.turkcell.authenticationservice.domain.Exceptions.UnauthorizedException;
import com.turkcell.authenticationservice.domain.models.RefreshToken;
import com.turkcell.authenticationservice.domain.models.User;
import com.turkcell.authenticationservice.port.TokenRepository;
import com.turkcell.authenticationservice.port.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    private final TokenRepository tokenAdapter;
    private final UserRepository userAdapter;
    private final JwtConfiguration jwtconf;
    public TokenService(TokenRepository tokenAdapter, UserRepository userAdapter, JwtConfiguration jwtconf) {
        this.tokenAdapter = tokenAdapter;
        this.userAdapter = userAdapter;
        this.jwtconf = jwtconf;
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRole()) // 👈 rolü ekledik
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtconf.getAccessTokenExpirationMs()))
                .signWith(
                        new SecretKeySpec(
                                jwtconf.getSecret().getBytes(StandardCharsets.UTF_8),
                                SignatureAlgorithm.HS256.getJcaName()
                        )
                )
                .compact();
    }

    public RefreshToken generateRefreshToken(User user){
        RefreshToken token =  new RefreshToken();
        token.setId(UUID.randomUUID());
        token.setUserId(user.getId());
        token.setCreatedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(jwtconf.getRefreshTokenExpirationDays(), ChronoUnit.DAYS));
        token.setRevoked(false);
        return token;
    }
    public boolean isRefreshTokenValid(RefreshToken refreshToken){
        return !refreshToken.getRevoked() && refreshToken.getExpiresAt().compareTo(Instant.now())>0;
    }
    public AuthResponse refreshAccessToken(RefreshRequest refreshRequest){
        RefreshToken token = tokenAdapter.findByToken(UUID.fromString(refreshRequest.refreshToken()));
        if(isRefreshTokenValid(token)){
            User user = userAdapter.findById(token.getUserId());
            return new AuthResponse(generateAccessToken(user), "Successfully Generated");
        }
        throw new UnauthorizedException("Refresh Token Deprecated");
    }

    public UUID isAccessTokenValid(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(jwtconf.getSecret().getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date exp = claims.getExpiration();
            if (new Date().before(exp)){
                return UUID.fromString(claims.getSubject());
            }
            else{
                throw new UnauthorizedException("Access token is not valid");
            }
        }
        catch (ExpiredJwtException e){
            throw new UnauthorizedException("Access token expired");
        }

    }


    public void revokeToken(UUID tokenId) {
        RefreshToken token = tokenAdapter.findByToken(tokenId);
            token.setRevoked(true);
            tokenAdapter.save(token);

    }
    public RequestContext extractContextFromAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(jwtconf.getSecret().getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            UUID userId = UUID.fromString(claims.getSubject());
            String role = claims.get("role", String.class);

            return new RequestContext(userId, role);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Access token expired");
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }
    }


}
