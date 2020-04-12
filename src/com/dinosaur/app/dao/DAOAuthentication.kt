package com.dinosaur.app.dao

import com.dinosaur.app.domain.User
import java.sql.Connection

class DAOAuthentication(private val connection: Connection) {
    fun getUserData(login: String): User {
        val prepareStatement = connection.prepareStatement(
                "SELECT login, hash, salt FROM USERS WHERE login=?"
        )
        prepareStatement.setString(1, login)
        val userSet = prepareStatement.executeQuery()
        userSet.next()

        return User(
                userSet.getString("login"),
                userSet.getString("hash"),
                userSet.getString("salt")
        )
    }
}