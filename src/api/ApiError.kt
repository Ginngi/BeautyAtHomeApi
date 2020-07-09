package com.bath.api

data class ApiError(
    val code: Int,
    val message: String
)

object ErrorCode {
    const val USER_NOT_FOUND = 100
}