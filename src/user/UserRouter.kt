package com.bath.user

import com.bath.api.ApiError
import com.bath.api.ErrorCode
import com.bath.api.toResponse
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.util.*

fun Route.user(userService: UserService) {

    route("/user") {
        get("/{id}") {
            try {
                val id = call.parameters["id"]
                val user = userService.getUserById(UUID.fromString(id))
                call.respond(toResponse(user))
            } catch (e: NoSuchElementException) {
                call.respond(toResponse(ApiError(ErrorCode.USER_NOT_FOUND, "User not found")))
            }
        }

        post("/") {
            val user: NewUser = call.receive()
            call.respond(toResponse(userService.insertUser(user)))
        }

        put("/") {
            val user: User = call.receive()
            call.respond(toResponse(userService.updateUser(user)))
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            val uuid = UUID.fromString(id)
            userService.deleteUser(uuid)
        }
    }

    get("/users") {
        val users = userService.getUsers()
        call.respond(toResponse(mapOf("users" to synchronized(users) { users.toList() })))
    }
}