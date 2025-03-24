//package com.cars24.ai_loan_assistance.util;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.SignatureException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.security.Key;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class JwtUtilTest {
//
//    @InjectMocks
//    private JwtUtil jwtUtil;
//
//    private final String secretKey = "MySuperSecureSecretKeyThatIsLongEnough12345";
//    private final String email = "test@example.com";
//    private final String userId = "12345";
//    private String token;
//
//    @BeforeEach
//    void setUp() {
//        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);
//        token = jwtUtil.generateToken(email, userId);
//    }
//
//    @Test
//    void testGenerateToken() {
//        String testEmail = "test@example.com";  // Renamed to avoid shadowing
//        String testUserId = "12345";  // Renamed to avoid shadowing
//
//        String generatedToken = jwtUtil.generateToken(testEmail, testUserId);  // Renamed to avoid shadowing
//        assertNotNull(generatedToken, "Token should not be null");
//
//        // Check if JWT has 3 parts (Header, Payload, Signature)
//        assertEquals(3, generatedToken.split("\\.").length, "Generated token format is incorrect");
//
//        String extractedEmail = jwtUtil.extractEmail(generatedToken);
//        String extractedUserId = jwtUtil.extractUserId(generatedToken);
//
//        assertEquals(testEmail, extractedEmail, "Emails do not match!");
//        assertEquals(testUserId, extractedUserId, "User IDs do not match!");
//        assertTrue(jwtUtil.validateToken(generatedToken, testEmail), "Token validation failed!");
//    }
//
//    @Test
//    void testExtractEmail() {
//        String extractedEmail = jwtUtil.extractEmail(token);
//        assertEquals(email, extractedEmail);
//    }
//
//    @Test
//    void testExtractUserId() {
//        String extractedUserId = jwtUtil.extractUserId(token);
//        assertEquals(userId, extractedUserId);
//    }
//
//    @Test
//    void testGenerateTokenWithNullEmail() {
//        assertThrows(IllegalArgumentException.class, () -> jwtUtil.generateToken(null, userId));
//    }
//
//    @Test
//    void testGenerateTokenWithNullUserId() {
//        assertThrows(IllegalArgumentException.class, () -> jwtUtil.generateToken(email, null));
//    }
//
//    @Test
//    void testValidateTokenWithWrongEmail() {
//        String wrongEmail = "wrong@example.com";
//        assertFalse(jwtUtil.validateToken(token, wrongEmail));
//    }
//
//    @Test
//    void testValidateToken() {
//        assertTrue(jwtUtil.validateToken(token, email));
//    }
//
//    @Test
//    void testValidateTokenWithTamperedToken() {
//        String tamperedToken = token.replace("id", "tampered");
//        assertThrows(SignatureException.class, () -> jwtUtil.extractEmail(tamperedToken));
//    }
//
//    @Test
//    void testIsTokenExpired() {
//        String expiredToken = createExpiredToken();
//        assertThrows(ExpiredJwtException.class, () -> jwtUtil.extractEmail(expiredToken));
//    }
//
//    @Test
//    void testExtractEmailWithMalformedToken() {
//        String malformedToken = Jwts.builder()
//                .claims(Map.of("id", userId)) // Missing subject
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 10000))
//                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
//                .compact();
//        assertThrows(IllegalArgumentException.class, () -> jwtUtil.extractEmail(malformedToken));
//    }
//
//    @Test
//    void testExtractUserIdWithMissingIdClaim() {
//        String tokenWithoutId = Jwts.builder()
//                .subject(email)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 10000))
//                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
//                .compact();
//        assertNull(jwtUtil.extractUserId(tokenWithoutId));
//    }
//
//    private String createExpiredToken() {
//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
//
//        return io.jsonwebtoken.Jwts.builder()
//                .subject(email) // Use the new fluent API for subject
//                .issuedAt(Date.from(Instant.now().minus(100, ChronoUnit.SECONDS))) // Use the new fluent API for issuedAt
//                .expiration(Date.from(Instant.now().minus(10, ChronoUnit.SECONDS))) // Use the new fluent API for expiration
//                .signWith(key) // Use the new fluent API for signWith
//                .compact();
//    }
//}

package com.cars24.ai_loan_assistance.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private final String secretKey = "MySuperSecureSecretKeyThatIsLongEnough12345";
    private final String email = "test@example.com";
    private final String userId = "12345";
    private String token;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);
        token = jwtUtil.generateToken(email, userId);
    }

    @Test
    void testGenerateToken() {
        String testEmail = "test@example.com";
        String testUserId = "12345";

        String generatedToken = jwtUtil.generateToken(testEmail, testUserId);
        assertNotNull(generatedToken, "Token should not be null");
        assertEquals(3, generatedToken.split("\\.").length, "Generated token format is incorrect");

        String extractedEmail = jwtUtil.extractEmail(generatedToken);
        String extractedUserId = jwtUtil.extractUserId(generatedToken);

        assertEquals(testEmail, extractedEmail, "Emails do not match!");
        assertEquals(testUserId, extractedUserId, "User IDs do not match!");
        assertTrue(jwtUtil.validateToken(generatedToken, testEmail), "Token validation failed!");
    }

    @Test
    void testExtractEmail() {
        String extractedEmail = jwtUtil.extractEmail(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    void testExtractUserId() {
        String extractedUserId = jwtUtil.extractUserId(token);
        assertEquals(userId, extractedUserId);
    }

    @Test
    void testGenerateTokenWithNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.generateToken(null, userId));
    }

    @Test
    void testGenerateTokenWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.generateToken(email, null));
    }

    @Test
    void testValidateTokenWithWrongEmail() {
        String wrongEmail = "wrong@example.com";
        assertFalse(jwtUtil.validateToken(token, wrongEmail));
    }

    @Test
    void testValidateToken() {
        assertTrue(jwtUtil.validateToken(token, email));
    }

    @Test
    void testValidateTokenWithTamperedToken() {
        String tamperedToken = token.replace("id", "tampered");
        assertThrows(SecurityException.class, () -> jwtUtil.extractEmail(tamperedToken));
    }

    @Test
    void testIsTokenExpired() {
        String expiredToken = createExpiredToken();
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.extractEmail(expiredToken));
    }

    @Test
    void testExtractEmailWithMalformedToken() {
        String malformedToken = Jwts.builder()
                .claims(Map.of("id", userId)) // Missing subject
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.extractEmail(malformedToken));
    }

    @Test
    void testExtractUserIdWithMissingIdClaim() {
        String tokenWithoutId = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        assertNull(jwtUtil.extractUserId(tokenWithoutId));
    }

    private String createExpiredToken() {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(Instant.now().minus(100, ChronoUnit.SECONDS)))
                .expiration(Date.from(Instant.now().minus(10, ChronoUnit.SECONDS)))
                .signWith(key)
                .compact();
    }

    @Test
    void testGetSigningKeyWithEmptySecret() {
        // Setup
        String originalSecret = secretKey;
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "");

        // Test
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            // Access private method using reflection
            ReflectionTestUtils.invokeMethod(jwtUtil, "getSigningKey");
        });

        // Verify exception message
        assertEquals("JWT Secret Key is null or empty! Check environment variables or properties file.", exception.getMessage());

        // Restore original secret
        ReflectionTestUtils.setField(jwtUtil, "secretKey", originalSecret);
    }

    @Test
    void testGetSigningKeyWithNullSecret() {
        // Setup
        String originalSecret = secretKey;
        ReflectionTestUtils.setField(jwtUtil, "secretKey", null);

        // Test
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            // Access private method using reflection
            ReflectionTestUtils.invokeMethod(jwtUtil, "getSigningKey");
        });

        // Verify exception message
        assertEquals("JWT Secret Key is null or empty! Check environment variables or properties file.", exception.getMessage());

        // Restore original secret
        ReflectionTestUtils.setField(jwtUtil, "secretKey", originalSecret);
    }

    @Test
    void testValidateTokenWithMalformedToken() {
        // Test with a completely invalid token
        String malformedToken = "this.is.not.a.valid.token";
        assertFalse(jwtUtil.validateToken(malformedToken, email));
    }

    @Test
    void testExtractUserIdWithExpiredToken() {
        String expiredToken = createExpiredToken();
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.extractUserId(expiredToken));
    }

    @Test
    void testExtractUserIdWithTamperedToken() {
        String tamperedToken = token.replace("id", "tampered");
        assertThrows(SecurityException.class, () -> jwtUtil.extractUserId(tamperedToken));
    }

    @Test
    void testIsTokenExpiredMethod() {
        // Testing the private method through reflection
        String validToken = token; // This is your valid token from setUp
        Boolean result = ReflectionTestUtils.invokeMethod(jwtUtil, "isTokenExpired", validToken);
        assertFalse(result, "Valid token should not be expired");

        String expiredToken = createExpiredToken();
        assertThrows(ExpiredJwtException.class, () -> {
            ReflectionTestUtils.invokeMethod(jwtUtil, "isTokenExpired", expiredToken);
        });
    }

    @Test
    void testExtractClaimsWithInvalidSignature() {
        // Create a token with a valid but different key
        String differentKey = "ThisIsADifferentSecretKeyWithSufficientLength12345678901234567890";
        Key key = Keys.hmacShaKeyFor(differentKey.getBytes());

        String tokenWithDifferentSignature = Jwts.builder()
                .subject(email)
                .claims(Map.of("id", userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(key)
                .compact();

        // We should expect SignatureException or any JwtException subclass
        assertThrows(SecurityException.class, () -> {
            // Access private method using reflection
            ReflectionTestUtils.invokeMethod(jwtUtil, "extractClaims", tokenWithDifferentSignature);
        });
    }

    @Test
    void testExtractClaimsWithMalformedToken() {
        String malformedToken = "not.a.valid.token";

        assertThrows(MalformedJwtException.class, () -> {
            // Access private method using reflection
            ReflectionTestUtils.invokeMethod(jwtUtil, "extractClaims", malformedToken);
        });
    }

    @Test
    void testTokenWithCustomExpirationTime() {

        // Store original value
        Long originalExpiration = (Long) ReflectionTestUtils.getField(JwtUtil.class, "expirationTime");

        try {
            // Set custom expiration time (5 minutes)
            long customExpiration = 300000;
            ReflectionTestUtils.setField(JwtUtil.class, "expirationTime", customExpiration);

            // Generate token
            String customToken = jwtUtil.generateToken(email, userId);

            // Verify token is valid
            assertTrue(jwtUtil.validateToken(customToken, email));

            // Verify claims
            Claims claims = ReflectionTestUtils.invokeMethod(jwtUtil, "extractClaims", customToken);

            // Calculate expected expiration (within 1 second tolerance)
            long expectedExpiration = Instant.now().plusMillis(customExpiration).toEpochMilli();
            long actualExpiration = claims.getExpiration().getTime();

            // Allow for small timing differences (1 second)
            assertTrue(Math.abs(expectedExpiration - actualExpiration) < 1000,
                    "Expiration time should be approximately " + customExpiration + " milliseconds from now");
        } finally {
            // Restore original value
            ReflectionTestUtils.setField(JwtUtil.class, "expirationTime", originalExpiration);
        }
    }

    @Test
    void testValidateTokenWithNullToken() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.validateToken(null, email));
    }

    @Test
    void testValidateTokenWithEmptyToken() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.validateToken("", email));
    }

    @Test
    void testValidateTokenWithNullEmail() {
        assertFalse(jwtUtil.validateToken(token, null));
    }
}