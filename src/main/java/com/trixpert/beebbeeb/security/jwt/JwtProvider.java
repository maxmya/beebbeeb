package com.trixpert.beebbeeb.security.jwt;

import com.trixpert.beebbeeb.security.UserPrinciple;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Transactional
@Component
public class JwtProvider {

    @Value("${app.auth.token.secret}")
    private String jwtSecret;

    @Value("${app.auth.token.expiration}")
    private Integer jwtExpiration;


    public String generateToken(Authentication authentication) {

        UserPrinciple principle = (UserPrinciple) authentication.getPrincipal();

        String token = "";

        if (jwtSecret != null) {
            List<String> roles = new ArrayList<>();
            principle.getUser().getRoles().forEach(role -> roles.add(role.getName()));

            Map<String, Object> claims = new HashMap<>();
            claims.put("iat", new Date());
            claims.put("name", principle.getUser().getName());
            claims.put("exp", new Date().getTime() + jwtExpiration);
            claims.put("username", principle.getUsername());
            claims.put("roles", roles);

            token = Jwts.builder()
                    .setSubject(principle.getUsername())
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();

        }

        return token;
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().get("username").toString();
    }


    public boolean validateJwtToken(String authToken) {
        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

            return (Instant.now().toEpochMilli() < claimsJws.getBody().getExpiration().toInstant().toEpochMilli());

        } catch (SignatureException
                | IllegalArgumentException
                | UnsupportedJwtException
                | ExpiredJwtException
                | MalformedJwtException e) {

            e.printStackTrace();
        }

        return false;
    }


}
