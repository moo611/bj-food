package com.example.bjfood.config.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "jindesongabcdefghijklmnopqrstuvwxyzjindesongabcdefghijklmnopqrstuvwxyzjindesongabcdefghijklmnopqrstuvwxyz";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10小时

    // 生成 JWT Token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) throws JwtException {
        try {
            return extractAllClaims(token).getSubject();
        } catch (JwtException e) {
            // 抛出异常，供外层捕获处理
            throw new JwtException("Failed to extract username from token: " + e.getMessage());
        }
    }

    // 验证 Token 是否过期
    public Boolean isTokenExpired(String token) throws JwtException {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (JwtException e) {
            throw new JwtException("Failed to check token expiration: " + e.getMessage());
        }
    }

    // 校验 Token 的合法性
    public Boolean validateToken(String token, String username) throws JwtException {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (JwtException e) {
            throw new JwtException("Token validation failed: " + e.getMessage());
        }
    }

    // 解析 Token，捕获各种 JWT 解析异常
    private Claims extractAllClaims(String token) throws JwtException {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT token has expired");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed JWT token");
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty");
        }
    }
}
