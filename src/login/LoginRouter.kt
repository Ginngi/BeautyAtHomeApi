package com.bath.login

import com.bath.api.ApiError
import com.bath.api.ErrorCode
import com.bath.api.toResponse
import com.bath.user.NewUser
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import java.util.*

fun Route.login(service: LoginService) {
    route("/signup") {
        post("/") {
            val newUser: NewUser = call.receive()
            val signUpResponse = service.signUp(newUser)

            call.respond(toResponse(signUpResponse))
        }
    }

    route("/login") {
        post("/") {
            try {
                val loginInfo: LoginInfo = call.receive()
                val signUpResponse = service.login(loginInfo)

                call.respond(toResponse(signUpResponse))
            } catch (e: NoSuchElementException) {
                call.respond(toResponse(ApiError(ErrorCode.USER_NOT_FOUND, "User not found")))
            }
        }
    }
}