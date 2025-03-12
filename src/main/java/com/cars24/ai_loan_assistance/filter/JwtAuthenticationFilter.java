package com.cars24.ai_loan_assistance.filter;

import com.cars24.ai_loan_assistance.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain )
            throws ServletException, IOException {

        String requestPath = request.getServletPath();
        // Skip JWT validation for public endpoints such as login and signup.
        if (requestPath.startsWith("/api/login") || requestPath.startsWith("/api/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

        // Attempt to get the token from cookies first
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Fallback to the Authorization header if no token was found in cookies
        if (token == null) {
            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }
        }

        if (token == null) {
            logger.debug("No JWT token found in request for path: " + requestPath);
        } else {
            String email = jwtUtil.extractEmail(token);
            logger.debug("Extracted email from token: " + email);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (userDetails != null && jwtUtil.validateToken(token, email)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        logger.debug("User authenticated successfully: " + email);
                    } else {
                        logger.debug("Invalid JWT token for email: " + email);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                        return;
                    }
                } catch (UsernameNotFoundException exc) {
                    logger.debug("User not found for email: " + email);
                    Cookie cookie = new Cookie("token", null);
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}