package com.cars24.ai_loan_assistance.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private Key getSigningKey() {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalStateException("JWT Secret Key is null or empty! Check environment variables or properties file.");
        }
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email, String userId) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "id", userId
//                  Claims are stored in the Payload part of the JWT
                ))
                .setSubject(email) //The Subject represents who the token is about.
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                //The signature ensures that the token is authentic and has not been tampered with.
                .compact();
    }


    public boolean validateToken(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }
      //Whether the token belongs to the given email using extractEmail().
// EXPIRY


    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }


    public String extractUserId(String token) {
        return (String) extractClaims(token).get("id");
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}