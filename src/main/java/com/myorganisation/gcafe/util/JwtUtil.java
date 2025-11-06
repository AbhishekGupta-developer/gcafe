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
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }

    public String extractEmail(String token) {
        return parse(token).getPayload().getSubject();
    }

    // Signup JWT logic here
    public String generateSignupToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim(PURPOSE_CLAIM, SIGNUP_PURPOSE)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + SIGNUP_EXPIRATION))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isValidSignupToken(String token) {
        try {
            Claims c = parse(token).getPayload();
            return SIGNUP_PURPOSE.equals(c.get(PURPOSE_CLAIM, String.class));
        } catch(JwtException e) {
            return false;
        }
    }

    // Auth/Signin/Login JWT logic here
    public String generateAuthToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim(PURPOSE_CLAIM, AUTH_PURPOSE)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + AUTH_EXPIRATION))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isValidAuthToken(String token) {
        try {
            Claims c = parse(token).getPayload();
            return AUTH_PURPOSE.equals(c.get(PURPOSE_CLAIM, String.class));
        } catch(JwtException e) {
            return false;
        }
    }

    // Password reset JWT logic here
    public String generatePasswordResetToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim(PURPOSE_CLAIM, PASSWORD_RESET_PURPOSE)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + PASSWORD_RESET_EXPIRATION))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isValidPasswordResetToken(String token) {
        try {
            Claims c = parse(token).getPayload();
            return PASSWORD_RESET_PURPOSE.equals(c.get(PURPOSE_CLAIM, String.class));
        } catch(JwtException e) {
            return false;
        }
    }

}
