package com.dinosaur.app.service

import com.dinosaur.app.ExitCodes
import com.dinosaur.app.dao.DAOAccounting
import com.dinosaur.app.domain.Session
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AccountingService(private val daoAccounting: DAOAccounting) {

    private var session: Session? = null

    fun accounting(
        user_id: Int,
        permission_id: Int,
        ds: String,
        de: String,
        vol: String
    ): ExitCodes {

        val volInt = vol.toInteger() ?: return ExitCodes.INVALID_ACTIVITY
        val dateStart = ds.toDate() ?: return ExitCodes.INVALID_ACTIVITY
        val dateEnd = de.toDate() ?: return ExitCodes.INVALID_ACTIVITY

        return if (dateEnd >= dateStart && volInt >= 0) {
            // store session data into table
            session = Session(
                    user_id, permission_id, dateStart, dateEnd, volInt
            )
            daoAccounting.addSession(session!!)
            ExitCodes.SUCCESS
        } else {
            ExitCodes.INVALID_ACTIVITY
        }
    }

    private fun String.toDate(pattern: String = "yyyy-MM-dd"): LocalDate? = try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    } catch (e: DateTimeParseException) { // если дата некорректная возвращаем null
        null
    }

    private fun String.toInteger(): Int? = try {
        this.toInt()
    } catch (e: NumberFormatException) {
        null
    }
}
