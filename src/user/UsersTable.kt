package com.bath.user

import org.jetbrains.exposed.dao.id.UUIDTable

private const val NAME = "name"
private const val EMAIL = "email"
private const val PASSWORD = "password"

object Users : UUIDTable() {
    val name = varchar(NAME, 50)
    val email = varchar(EMAIL, 50)
    val password = varchar(PASSWORD, 50)
}