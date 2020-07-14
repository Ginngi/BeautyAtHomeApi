package com.bath.login

import com.bath.auth.AuthJWTProvider
import com.bath.db.query
import com.bath.user.NewUser
import com.bath.user.UserService
import com.bath.user.Users
import com.bath.user.toUser
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

class LoginService(
    private val userService: UserService
) {

    suspend fun signUp(newUser: NewUser): SignUpResponse {
        val user = userService.insertUser(newUser)
        return SignUpResponse(user, AuthJWTProvider.makeToken(user))
    }

    suspend fun login(loginInfo: LoginInfo): SignUpResponse = query {
        Users.select { (Users.email eq loginInfo.email) and (Users.password eq loginInfo.password) }
            .map { toUser(it) }
            .map { SignUpResponse(it, AuthJWTProvider.makeToken(it)) }
            .first()
    }
}