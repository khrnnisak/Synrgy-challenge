package com.example.FBJV24001115synergy7indbinfoodch7.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    
    
    public String generateToken(Authentication authentication) {
        String username;
        if (authentication.getPrincipal() instanceof UserDetailsImpl userPrincipal) {
            username = userPrincipal.getUsername();
        }else if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            username = oidcUser.getEmail();
        }else if (authentication.getPrincipal() instanceof DefaultOAuth2User defaultOAuth2User) {
            username = defaultOAuth2User.getAttribute("login");
        }else{
            throw new IllegalArgumentException("Unsupported principal type");
        }
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+jwtExpiration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String jwt) {
        return Jwts.parserBuilder()
            .setSigningKey(key()).build()
            .parseClaimsJws(jwt)
            .getBody()
            .getSubject();
    }

    private Key key() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
