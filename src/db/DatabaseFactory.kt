package com.bath.db

import com.bath.user.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val DB_URL = "jdbc:postgresql://postgres:5432/postgres"
    private val DB_DRIVER = "org.postgresql.Driver"
    private val DB_USER = "postgres"
    private val DB_PASSWORD = ""

    fun init() {
        Database.connect(initDataSource())

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users)
        }
    }

    private fun initDataSource(): HikariDataSource {
        val config = HikariConfig()

        config.driverClassName = DB_DRIVER
        config.jdbcUrl = DB_URL
        config.username = DB_USER
        config.password = DB_PASSWORD
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }
}