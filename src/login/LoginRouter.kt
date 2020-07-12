package com.bath.login

import com.bath.api.toResponse
import com.bath.user.NewUser
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.login(service: LoginService) {
    route("/signup") {
        post("/") {
            val newUser: NewUser = call.receive()
            val signUpResponse = service.signUp(newUser)

            call.respond(toResponse(signUpResponse))
        }
    }
}