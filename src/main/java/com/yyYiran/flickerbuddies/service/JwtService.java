package com.yyYiran.flickerbuddies.service;

import com.yyYiran.flickerbuddies.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    final private String SECRET_KEY = "8hzu2O/h5DbPMAVj/tutkt2xAXkjF3kzhcfOHHr37USKY0Y1S1iIiPPGzcCsBGk8";
    private static final long EXPIRATION_TIME = 3600000;

    public String extractUsername(String token) {
        String username = extractAllClaims(token).getSubject();
        System.err.println("extractusername: " + username);
        return username;
    }


    // TODO: make sure this user exists in JwtAuthFilter
    public String generateToken(UserDetails userDetails){
        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (userDetails == null || token==null){
            return false;
        }
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration()
                .before(new Date());
    }

    // Helper methods to extract claims or specific claim
    // extractAllClaims: extract claims from payloads (information about entity/user)
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    private <T>T extractClaim(String token, Function<Claims, T> claimSelector){
//        Claims claims = extractAllClaims(token);
//        return claimSelector.apply(claims);
//    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // Get signing key to ensure client is who it claims to be, and msg unaltered

}
