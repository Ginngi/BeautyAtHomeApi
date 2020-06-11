package com.bath.user

import com.bath.db.query
import com.bath.user.Users.id
import com.bath.user.Users.name
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserService {

    suspend fun createUser() = query {
        Users.insert {
            it[name] = "Gerard"
        }
    }

    suspend fun getUsers(): List<User> = query {
        Users.selectAll()
            .mapNotNull { toUser(it) }
    }

    fun toUser(row: ResultRow): User {
        return User(
            id = row[id].value,
            name = row[name]
        )
    }
}