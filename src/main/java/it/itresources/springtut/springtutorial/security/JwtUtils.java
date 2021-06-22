package it.itresources.springtut.springtutorial.security;

import io.jsonwebtoken.*;
import it.itresources.springtut.springtutorial.model.UserDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.signing.key.secret}")
    private String secret;

    @Value("${jwt.token.expiration.millis}")
    private int expirationMillis;

    public String generateJwtToken(Authentication authentication) {
        UserDetailImpl principal = (UserDetailImpl) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMillis * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
