package com.management.managementservice.service.jwt;

import com.management.managementservice.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtService {


    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    @Value("${app.jwt-secret}")
    String SECRET;



    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }


    public String generateToken(User user) {
        return createToken(user);
    }

    private String createToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s,%s", user.getId(), user.getEmail(), user.getUsername()))
                .setIssuer("New-Security-Jwt-User")
                .claim("roles", new ArrayList<>(user.getRoles()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getCustomExpirationTime())
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Date getCustomExpirationTime() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, 1440);
        return c.getTime();
    }
}
