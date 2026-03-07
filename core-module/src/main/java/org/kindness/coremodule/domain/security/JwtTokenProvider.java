package org.kindness.coremodule.domain.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
public class JwtTokenProvider {
    private final SecretKey secretKey = Keys.hmacShaKeyFor("topSecretKeyTODOOO".getBytes());
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
}
