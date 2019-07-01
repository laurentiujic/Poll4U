package org.x1c1b.poll4u.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${poll4u.security.secret}")
    private String secret;

    @Value("${poll4u.security.expiration}")
    private int expiration;

    @Override
    public String generate(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + expiration);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public boolean validate(String token) {

        try {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;

        } catch (SignatureException ex) {

            LOGGER.error("Invalid JWT signature");

        } catch (MalformedJwtException ex) {

            LOGGER.error("Invalid JWT token");

        } catch (ExpiredJwtException ex) {

            LOGGER.error("Expired JWT token");

        } catch (UnsupportedJwtException ex) {

            LOGGER.error("Unsupported JWT token");

        } catch (IllegalArgumentException ex) {

            LOGGER.error("JWT claims string is empty");
        }

        return false;
    }

    @Override
    public Long getPayloadFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
