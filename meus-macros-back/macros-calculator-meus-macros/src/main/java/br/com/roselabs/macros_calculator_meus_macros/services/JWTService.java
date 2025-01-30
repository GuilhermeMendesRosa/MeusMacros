package br.com.roselabs.macros_calculator_meus_macros.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JWTService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

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

    public UUID getUUIDFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .withIssuer("meus-macros")
                    .build()
                    .verify(token);

            return UUID.fromString(decodedJWT.getSubject());
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}
