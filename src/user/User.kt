package com.bath.user

import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val password: String
)

data class NewUser(
    val name: String,
    val email: String,
    val password: String
)