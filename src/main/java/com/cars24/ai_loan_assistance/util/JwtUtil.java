
package com.cars24.ai_loan_assistance.util;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final String INVALID_TOKEN_MESSAGE = "Invalid token: ";
    private static  long expirationTime = 86400000;

    private Key getSigningKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("JWT Secret Key is null or empty! Check environment variables or properties file.");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email, String userId) {
        if (email == null || userId == null) {
            throw new IllegalArgumentException("Both email and userId must not be null.");
        }
        return Jwts.builder()
                .claims(Map.of("id", userId))
                .subject(email)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token, String email) {
        try {
            return extractEmail(token).equals(email) && !isTokenExpired(token);
        } catch (ExpiredJwtException | SecurityException | MalformedJwtException e) {
            return false; // Token is invalid
        }
    }

    public String extractEmail(String token) {
        try {
            Claims claims = extractClaims(token);
            String subject = claims.getSubject();
            if (subject == null || subject.isEmpty()) {
                throw new IllegalArgumentException("Invalid token: Missing or empty 'subject' claim.");
            }
            return subject;
        } catch (ExpiredJwtException | SecurityException | MalformedJwtException e) {
            // Propagate specific JWT exceptions directly
            throw e;
        } catch (Exception e) {
            // Wrap unexpected exceptions in IllegalArgumentException
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE + e.getMessage(), e);
        }
    }

    public String extractUserId(String token) {
        try {
            return (String) extractClaims(token).get("id");
        } catch (ExpiredJwtException | SecurityException| MalformedJwtException e) {
            // Propagate specific JWT exceptions directly
            throw e;
        } catch (Exception e) {
            // Wrap unexpected exceptions in IllegalArgumentException
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE + e.getMessage(), e);
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | SecurityException | MalformedJwtException e) {
            throw e; // Propagate expired token exception
        }catch (Exception e) {
            // Wrap unexpected exceptions in IllegalArgumentException
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE + e.getMessage(), e);
        }
    }
}