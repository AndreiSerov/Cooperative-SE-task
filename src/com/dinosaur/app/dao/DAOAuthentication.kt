package com.dinosaur.app.dao

import com.dinosaur.app.domain.User
import java.sql.Connection

class DAOAuthentication(private val connection: Connection) {
    fun getUserData(login: String): User? {
        val statement = connection.prepareStatement(
                "SELECT * FROM USERS WHERE login=?"
        )
        // close when block ends TODO
        statement.setString(1, login)
        val userSet = statement.executeQuery()
        // check that user in db
        if (userSet.next()) {
            return User(
                    userSet.getInt("id"),
                    userSet.getString("login"),
                    userSet.getString("hash"),
                    userSet.getString("salt")
            )
        }
        // if user not in db return null
        return null
    }
}