package com.bath.user

import com.bath.db.query
import com.bath.user.Users.email
import com.bath.user.Users.id
import com.bath.user.Users.name
import com.bath.user.Users.password
import org.jetbrains.exposed.sql.*
import java.util.*

class UserService {

    suspend fun insertUser(user: NewUser): User = query {
        val uuid = Users.insertAndGetId {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
        }.value

        User(uuid, user.name, user.email)
    }

    suspend fun updateUser(user: User) = query {
        Users.update({ Users.id eq user.id }) {
            it[name] = user.name
            it[email] = user.email
        }
    }

    suspend fun getUsers(): List<User> = query {
        Users.selectAll()
            .mapNotNull { toUser(it) }
    }

    suspend fun getUserById(uuid: String): User =
        getUserById(UUID.fromString(uuid))

    suspend fun getUserById(uuid: UUID): User = query {
        Users.select { Users.id eq uuid }
            .map { toUser(it) }
            .first()
    }

    suspend fun deleteUser(uuid: UUID): Boolean = query {
        Users.deleteWhere { Users.id eq uuid } > 0
    }
}