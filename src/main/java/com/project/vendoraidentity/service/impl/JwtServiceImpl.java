package com.project.vendoraidentity.service.impl;


import com.project.vendoraidentity.entity.User;
import com.project.vendoraidentity.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    // ⚠️ In production, store this in env/secret manager
    private static final String SECRET_KEY = "your-very-secure-and-long-jwt-secret-key-please-change-it";

    private final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .claim("provider", user.getAuthProvider())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(7200))) // 2 hours expiry
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
