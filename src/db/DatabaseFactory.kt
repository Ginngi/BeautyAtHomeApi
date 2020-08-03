package com.bath.db

import com.bath.user.Users
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

object DatabaseFactory {
    fun init() {
        Database.connect(initDataSource())

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users)
        }
    }

    private fun initDataSource(): HikariDataSource {
        val configuration = ConfigFactory.load().getConfig("postgres")

        val dBConfig = HikariConfig()

        val dbUri = URI(configuration.getString("url"))
        val username: String = dbUri.userInfo.split(":")[0]
        val password: String = dbUri.userInfo.split(":")[1]

        dBConfig.driverClassName = configuration.getString("driver")
        dBConfig.jdbcUrl = getUrlFromHeroku(dbUri)
        dBConfig.username = username
        dBConfig.password = password
        dBConfig.maximumPoolSize = 3
        dBConfig.isAutoCommit = false
        dBConfig.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        dBConfig.validate()

        return HikariDataSource(dBConfig)
    }

    private fun getUrlFromHeroku(url: URI): String {
        return "jdbc:postgresql://" + url.host + ':' + url.port + url.path.toString()
    }
}