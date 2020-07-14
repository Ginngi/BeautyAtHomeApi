package com.bath.user

import org.jetbrains.exposed.sql.ResultRow

fun toUser(row: ResultRow): User {
    return User(
        id = row[Users.id].value,
        name = row[Users.name],
        email = row[Users.email],
        password = row[Users.password]
    )
}