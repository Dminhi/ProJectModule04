package com.example.project.securiry.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@Component
@Slf4j
public class JWTProvider {
    @Value("${jwt.expired-time}0")
    private long expired;
    @Value("${jwt.secret-key}")
    private String secretKey;
    public String generateAccessToken(UserDetails userDetails){
        Date today = new Date();
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime()+expired)) // 24 h
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT: message error expired:", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT: message error unsupported:", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT: message error not formated:", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT: message error signature not math:", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT: message claims empty or argument invalid: ", e.getMessage());
        }
        return false;
    }
    public String getUserNameFromToken(String token){
        return  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
