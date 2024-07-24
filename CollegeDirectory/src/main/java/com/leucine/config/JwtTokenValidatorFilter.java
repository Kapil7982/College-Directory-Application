package com.leucine.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidatorFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        logger.debug("JwtTokenValidatorFilter is being executed");
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        logger.debug("JWT Header: " + jwt);

        if(jwt != null && jwt.startsWith("Bearer ")) {
            try {
                jwt = jwt.substring(7);
                logger.debug("JWT: " + jwt);

                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                
                String username = String.valueOf(claims.get("username"));
                String role = (String)claims.get("role");
                
                logger.debug("Username: " + username);
                logger.debug("Role: " + role);

                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                logger.debug("Authorities: " + authorities);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                logger.debug("Authentication set in SecurityContextHolder");
                
            } catch (Exception e) {
                logger.error("Error processing JWT token", e);
                SecurityContextHolder.clearContext();
                throw new BadCredentialsException("Invalid Token received..");
            }
        } else {
            logger.debug("No JWT token found in request headers");
        }
        
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/auth/signIn");
    }
}