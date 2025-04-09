package com.finace.management.utils;

import com.finace.management.entity.AppUser;
import com.finace.management.exception.AppException;
import com.finace.management.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${spring.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${spring.security.jwt.expiration}")
    private long EXPIRATION_TIME;

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        }catch (Exception e){
            throw new AppException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        }
    }

    // extract each claim
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.
                parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generate with just user details
    public String generateToken(AppUser userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generate with extra claims
    public String generateToken(Map<String, Object> extraClaims,
                                AppUser userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token
    public boolean isTokenValid(String token, AppUser userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
