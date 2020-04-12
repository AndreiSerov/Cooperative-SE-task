package com.dinosaur.app.service

import org.apache.logging.log4j.kotlin.KotlinLogger
import java.sql.Connection
import java.sql.DriverManager

class DBService(private val log: KotlinLogger) {
    private val URL: String = System.getenv("H2URL") ?: "jdbc:h2:./default"
    private val USER: String = System.getenv("H2LOGIN") ?: "sa"
    private val PASSWORD: String = System.getenv("H2PASS") ?: ""

    var connection: Connection? = null

    fun getConnection() {
        log.info("Connect to Data Base")
        connection = DriverManager.getConnection(URL, USER, PASSWORD)
    }

    fun close() {
        log.info("Close Data Base connection")
        if (connection != null) {
            connection!!.close()
        }
    }
    
}