package com.dinosaur.app.service

import org.apache.logging.log4j.kotlin.KotlinLogger
import org.flywaydb.core.Flyway
import java.sql.Connection
import java.sql.DriverManager

class DBService(private val log: KotlinLogger) : AutoCloseable {
    private val url: String = System.getenv("H2URL") ?: "jdbc:h2:./default"
    private val user: String = System.getenv("H2LOGIN") ?: "sa"
    private val pass: String = System.getenv("H2PASS") ?: ""

    var connection: Connection? = null

    fun getConnection() {
        migrate()

        log.info("Connect to Data Base")
        connection = DriverManager.getConnection(url, user, pass)
    }

    private fun migrate() {
        Flyway.configure()
                .dataSource(url, user, pass)
                .locations(
                        "filesystem:./src/main/resources/db"
                )
                .load()
                .migrate()
    }

    override fun close() {
        log.info("Close Data Base connection")
        if (connection != null) {
            connection!!.close()
        }
    }

}