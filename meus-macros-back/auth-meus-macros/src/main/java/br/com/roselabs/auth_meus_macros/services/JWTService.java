package br.com.roselabs.auth_meus_macros.services;

import br.com.roselabs.auth_meus_macros.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return JWT.create()
                .withIssuer("meus-macros")
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                    .withIssuer("meus-macros")
                    .acceptExpiresAt(System.currentTimeMillis())
                    .build();

            verifier.verify(token);

            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String getIssuerFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .withIssuer("meus-macros")
                    .build()
                    .verify(token);

            return decodedJWT.getIssuer();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}
