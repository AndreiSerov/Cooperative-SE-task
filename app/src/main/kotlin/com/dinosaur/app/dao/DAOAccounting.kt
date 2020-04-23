package com.dinosaur.app.dao

import com.dinosaur.app.domain.Session
import java.sql.Connection
import java.sql.Date

class DAOAccounting(private val connection: Connection) {
    fun addSession(session: Session) {
        val query = """
            insert into SESSIONS(USER_ID, PERMISSION_ID, DS, DE, VOL)
                values (?, ?, ?, ?, ?);
        """.trimIndent()
        val statement = connection.prepareStatement(query)
        statement.setInt(1, session.userId)
        statement.setInt(2, session.permissionId)
        statement.setDate(3, Date.valueOf(session.ds))
        statement.setDate(4, Date.valueOf(session.de))
        statement.setInt(5, session.vol)
        statement.execute()
    }
}
