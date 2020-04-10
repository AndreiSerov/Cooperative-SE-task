package com.dinosaur.app.service

import org.apache.logging.log4j.kotlin.KotlinLogger
import java.sql.Connection
import java.sql.DriverManager

class DBService(private val log: KotlinLogger) {
    val URL: String = System.getenv("H2URL") ?: "jdbc:h2:./default"
    val USER: String = System.getenv("H2LOGIN") ?: "default"
    val PASSWORD: String = System.getenv("H2PASS") ?: "default"

    var connection: Connection? = null

    fun getConnection() {
        log.info("Connect tot Data Base")
        connection = DriverManager.getConnection(URL, USER, PASSWORD)
    }

    fun close() {
        log.info("Close Data Base onnection")
        if (connection != null) {
            connection!!.close()
        }
    }

    
    fun getUser(login: String) {

    }
}