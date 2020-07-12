package com.bath.login

import com.bath.auth.AuthJWTProvider
import com.bath.user.NewUser
import com.bath.user.UserService

class LoginService(
    private val userService: UserService
) {

    suspend fun signUp(newUser: NewUser): SignUpResponse {
        val user = userService.insertUser(newUser)
        return SignUpResponse(user, AuthJWTProvider.makeToken(user))
    }
}