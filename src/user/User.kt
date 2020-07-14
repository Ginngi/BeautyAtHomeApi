package com.bath.user

import io.ktor.auth.Principal
import java.util.*

data class User(
    val id: UUID,
    val name: String,
    val email: String
) : Principal

data class NewUser(
    val name: String,
    val email: String,
    val password: String
)