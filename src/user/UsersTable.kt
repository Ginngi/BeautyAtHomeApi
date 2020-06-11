package com.bath.user

import org.jetbrains.exposed.dao.id.UUIDTable

private const val NAME = "name"

object Users : UUIDTable() {
    val name = varchar(NAME, 50)
}