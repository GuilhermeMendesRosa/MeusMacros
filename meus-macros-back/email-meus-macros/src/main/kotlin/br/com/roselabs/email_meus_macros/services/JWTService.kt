package br.com.roselabs.email_meus_macros.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService {

    @Value("\${security.jwt.secret-key}")
    private lateinit var secretKey: String

    fun validateToken(token: String): Boolean {
        return try {
            val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("meus-macros")
                .acceptExpiresAt(System.currentTimeMillis())
                .build()
            verifier.verify(token)
            true
        } catch (exception: JWTVerificationException) {
            false
        }
    }

    fun getIssuerFromToken(token: String): String? {
        return try {
            val decodedJWT: DecodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("meus-macros")
                .build()
                .verify(token)
            decodedJWT.issuer
        } catch (exception: JWTVerificationException) {
            null
        }
    }

    fun getUUIDFromToken(token: String): UUID? {
        return try {
            val decodedJWT: DecodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("meus-macros")
                .build()
                .verify(token)
            UUID.fromString(decodedJWT.subject)
        } catch (exception: JWTVerificationException) {
            null
        }
    }
}
