package com.dinosaur.app.service

import org.apache.logging.log4j.kotlin.KotlinLogger
import java.io.Closeable
import java.sql.Connection
import java.sql.DriverManager

class DBService(private val log: KotlinLogger) : Closeable {
    private val url: String = System.getenv("H2URL") ?: "jdbc:h2:./default"
    private val user: String = System.getenv("H2LOGIN") ?: "sa"
    private val pass: String = System.getenv("H2PASS") ?: ""

    var connection: Connection? = null

    fun getConnection() {
        log.info("Connect to Data Base")
        connection = DriverManager.getConnection(url, user, pass)
    }

    override fun close() {
        log.info("Close Data Base connection")
        if (connection != null) {
            connection!!.close()
        }
    }

}