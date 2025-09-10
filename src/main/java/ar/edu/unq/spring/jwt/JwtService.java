package ar.edu.unq.spring.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
@Service
public class JwtService {

    private static final String SECRET_KEY = "eae7b21b468e44d356f3bc5e6cff8b768a8aba84eb10492bd7d2cf5cd07e83601f1c1b069f1bbfe05cdf049ae62c4f0ff73a22df661a9e7bb378ca33d32aa37b";

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken(token);
        return username != null && username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.getKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return this.getExpiration(token).before(new Date());
    }
}
