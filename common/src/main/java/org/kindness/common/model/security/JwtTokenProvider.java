package org.kindness.common.model.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

public class JwtTokenProvider {
    // TODO switch to file from env
    private final SecretKey secretKey = Keys.hmacShaKeyFor("topSecretKwewkqeqehufnewibwufwywuq7ebc!eyTODOOO".getBytes());
    private final long validityMs = Duration.ofHours(1).toMillis();

    // https://github.com/jwtk/jjwt?tab=readme-ov-file#quickstart
    public String createToken(Long userId, String email){
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validityMs))
                .signWith(secretKey)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // Используем тот же ключ, что и при создании
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Токен подделан, просрочен или поврежден
            return false;
        }
    }
}
