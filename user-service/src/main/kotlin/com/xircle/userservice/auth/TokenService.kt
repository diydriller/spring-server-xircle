package com.xircle.userservice.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWT.decode
import com.auth0.jwt.interfaces.DecodedJWT
import com.xircle.common.util.NumberUtil.Companion.ACCESS_TOKEN_DURATION
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenService(
    @Value("\${jwt.secret}") private val jwtSecret: String
) {
    fun verifyToken(jwtToken: String) {
        JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(jwtSecret))
            .build()
            .verify(jwtToken)
    }

    fun decodeToken(jwtToken: String): DecodedJWT {
        return decode(jwtToken)
    }

    fun createAccessToken(id: Long): String {
        return JWT.create()
            .withClaim("id", id)
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_DURATION))
            .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(jwtSecret))
    }
}