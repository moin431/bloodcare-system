package bd.edu.seu.bdms.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key = Keys.hmacShaKeyFor(
            "0123456789ABCDEF0123456789ABCDEF".getBytes()
    );

    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    public String generateToken(String email, String role){

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String getEmail(String token){
        return getClaims(token).getSubject();
    }

    public String getRole(String token){
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
