package com.example.vaksinasiservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "rahasia123456789012345678901234567890"; // min. 32 karakter
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }
}
