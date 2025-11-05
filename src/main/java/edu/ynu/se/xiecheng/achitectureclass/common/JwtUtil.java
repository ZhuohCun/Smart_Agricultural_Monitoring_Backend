package edu.ynu.se.xiecheng.achitectureclass.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "AWHUSWH983Y97EY329YF94Y9UY98Y870ADS"; // 密钥

    public static String generateToken(String username,long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private static boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().after(new Date());
    }

    public static boolean validateToken(String token) {
        return isTokenExpired(token);
    }

    public static boolean isValidJwt(String token) {
        try {
            return validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }
}