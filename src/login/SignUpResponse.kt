package com.bath.login

import com.bath.user.User

data class SignUpResponse(
    val user: User,
    val token: String
)