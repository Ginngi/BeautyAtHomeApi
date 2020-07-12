package com.bath

import com.bath.auth.AuthJWTProvider
import com.bath.db.DatabaseFactory
import com.bath.login.LoginService
import com.bath.login.login
import com.bath.user.UserService
import com.bath.user.user
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {
    val userService = UserService()
    val loginService = LoginService(userService)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
    }

    install(Authentication) {
        jwt {
            verifier(AuthJWTProvider.verifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim("id").asString()?.let { uuid ->
                    userService.getUserById(uuid)
                }
            }
        }
    }

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    DatabaseFactory.init()

    routing {
        user(userService)
        login(loginService)
    }
}