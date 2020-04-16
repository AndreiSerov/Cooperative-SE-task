package com.dinosaur.app.dao

import com.dinosaur.app.domain.Permission
import java.sql.Connection

class DAOAuthorization(private val connection: Connection) {
    fun confirmPermission(login: String,
                          role: String): MutableList<Permission>? {
        val permissions: MutableList<Permission> = mutableListOf()

        val query = """
            select PERMISSIONS.ID, LOGIN, RES, ROLE from PERMISSIONS
                JOIN USERS ON PERMISSIONS.USER_ID=USERS.ID
                where LOGIN=? AND ROLE=?;
        """.trimIndent()

        val statement = connection.prepareStatement(query)
        statement.setString(1, login)
        statement.setString(2, role)

        val permissionSet = statement.executeQuery()

        while (permissionSet.next()) {
            val permission = Permission(
                    permissionSet.getInt("id"),
                    permissionSet.getString("login"),
                    permissionSet.getString("res"),
                    permissionSet.getString("role")
            )
            permissions.add(permission)
        }

        if(permissions.isEmpty()) return null

        return permissions
    }
}