package com.cars24.ai_loan_assistance.filter;

import com.cars24.ai_loan_assistance.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContext securityContext;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        userDetails = new User("test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_withPublicEndpoint_shouldSkipAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/login");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void doFilterInternal_withPublicSignupEndpoint_shouldSkipAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/signup");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void doFilterInternal_withoutToken_shouldNotAuthenticate() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");
        when(request.getCookies()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilterInternal_withTokenInCookie_shouldAuthenticateUser() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");

        Cookie[] cookies = new Cookie[]{new Cookie("token", "valid-token")};
        when(request.getCookies()).thenReturn(cookies);

        when(jwtUtil.extractEmail("valid-token")).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtUtil.validateToken("valid-token", "test@example.com")).thenReturn(true);

        // Mock SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(securityContext).setAuthentication(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void doFilterInternal_withTokenInHeader_shouldAuthenticateUser() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");
        when(request.getCookies()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");

        when(jwtUtil.extractEmail("valid-token")).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtUtil.validateToken("valid-token", "test@example.com")).thenReturn(true);

        // Mock SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(securityContext).setAuthentication(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldReturnUnauthorized() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");
        when(request.getCookies()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");

        when(jwtUtil.extractEmail("invalid-token")).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtUtil.validateToken("invalid-token", "test@example.com")).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_withNonExistentUser_shouldReturnUnauthorized() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");
        when(request.getCookies()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");

        when(jwtUtil.extractEmail("valid-token")).thenReturn("nonexistent@example.com");
        when(userDetailsService.loadUserByUsername("nonexistent@example.com"))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());

        // Verify that cookie was cleared
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void doFilterInternal_withExistingAuthentication_shouldSkipAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");

        Cookie[] cookies = new Cookie[]{new Cookie("token", "valid-token")};
        when(request.getCookies()).thenReturn(cookies);

        when(jwtUtil.extractEmail("valid-token")).thenReturn("test@example.com");

        // Mock existing authentication
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilterInternal_withNullEmail_shouldNotAuthenticate() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/loans");

        Cookie[] cookies = new Cookie[]{new Cookie("token", "invalid-token")};
        when(request.getCookies()).thenReturn(cookies);

        when(jwtUtil.extractEmail("invalid-token")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }
}