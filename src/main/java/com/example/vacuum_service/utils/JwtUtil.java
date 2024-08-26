package com.example.vacuum_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "My jwt secret key";
    private final int EXPIRATION = 1000 * 60 * 60 * 10;

    public Claims extracAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return extracAllClaims(token).getSubject();
    }
    public List<String> extreactPrivileges(String token){
        return extracAllClaims(token).get("privileges", List.class);
    }
    public boolean isTokenExpired(String token){
        return extracAllClaims(token).getExpiration().before(new Date());
    }
}
