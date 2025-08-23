package com.archiving.archivingTool.config;

import com.archiving.archivingTool.mapper.UserPrincipalConverter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;
    @Autowired
    private UserPrincipalConverter userPrincipalConverter;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = userPrincipalConverter.convertFromAuthentication(authentication);

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> {
                    // Remove ALL "ROLE_" prefixes to avoid duplication
                    String cleanAuthority = authority;
                    while (cleanAuthority.startsWith("ROLE_")) {
                        cleanAuthority = cleanAuthority.substring(5);
                    }
                    return cleanAuthority;
                })
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Extract roles from the claims
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("roles");

        return roles != null ? roles : List.of();
    }
}
