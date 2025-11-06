package com.myorganisation.gcafe.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String PURPOSE_CLAIM = "purpose";
    private static final String SIGNUP_PURPOSE = "signup";
    private static final String AUTH_PURPOSE = "auth";
    private static final String PASSWORD_RESET_PURPOSE = "passwordReset";

    private final long SIGNUP_EXPIRATION = 1000 * 60 * 5; // 5 minutes
    private final long AUTH_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours
    private final long PASSWORD_RESET_EXPIRATION = 1000 * 60 * 5; // 5 minutes

    private final String SECRET;
    private final SecretKey KEY;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
        SECRET = jwtSecret;
        KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
        AUTH_EXPIRATION = 1000 * 60 * 5; // Valid for 5 mins
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + AUTH_EXPIRATION))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        Claims body = getClaims(token);

        return body.getSubject();
    }

}
