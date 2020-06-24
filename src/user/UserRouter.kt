package com.bath.user

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.util.*

fun Route.user(userService: UserService) {

    route("/user") {
        get("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            val uuid = UUID.fromString(id)
            userService.getUserById(uuid)
        }

        post("/") {
            val user: NewUser = call.receive()
            call.respond(userService.insertUser(user))
        }

        put("/") {
            val user: User = call.receive()
            call.respond(userService.updateUser(user))
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            val uuid = UUID.fromString(id)
            userService.deleteUser(uuid)
        }
    }

    get("/users") {
        val users = userService.getUsers()
        call.respond(mapOf("users" to synchronized(users) { users.toList() }))
    }
}