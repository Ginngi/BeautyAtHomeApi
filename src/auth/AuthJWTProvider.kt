package com.bath.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.bath.user.User
import com.typesafe.config.ConfigFactory

object AuthJWTProvider {

    private val jwtConfig = ConfigFactory.load().getConfig("jwt")
    private val algorithm = Algorithm.HMAC512(jwtConfig.getString("secret"))

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(jwtConfig.getString("issuer"))
        .build()

    /**
     * Produce a token for this combination of User and Account
     */
    fun makeToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtConfig.getString("issuer"))
        .withClaim("id", user.id.toString())
        .sign(algorithm)
}