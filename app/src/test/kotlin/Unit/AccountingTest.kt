package com.dinosaur.app

import com.dinosaur.app.dao.DAOAccounting
import com.dinosaur.app.domain.Session
import com.dinosaur.app.service.AccountingService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import java.time.LocalDate
import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AccountingTest : Spek({
    describe("Accounting") {
        val userId = 1
        val permissionId = 1
        val ds = "2020-03-11"
        val de = "2020-03-12"
        val vol = "1"
        val session = Session(
                userId,
                permissionId,
                LocalDate.parse(ds),
                LocalDate.parse(de),
                vol.toInt()
        )
        val daoAccounting = mockk<DAOAccounting>()
        every {
            daoAccounting.addSession(session)
        } just Runs
        val accService = AccountingService(daoAccounting)

        it("Invalid Vol") {
            assertEquals(
                    ExitCodes.INVALID_ACTIVITY,
                    accService.accounting(userId, permissionId, ds, de, "asd")
            )
        }
        it("Invalid ds") {
            assertEquals(
                    ExitCodes.INVALID_ACTIVITY,
                    accService.accounting(userId, permissionId, "test", de, vol)
            )
        }
        it("Invalid de") {
            assertEquals(
                    ExitCodes.INVALID_ACTIVITY,
                    accService.accounting(userId, permissionId, ds, "test", vol)
            )
        }
        it("vol < 0") {
            assertEquals(
                    ExitCodes.INVALID_ACTIVITY,
                    accService.accounting(userId, permissionId, ds, de, "-1")
            )
        }
        it("ds > de") {
            assertEquals(
                    ExitCodes.INVALID_ACTIVITY,
                    accService.accounting(userId, permissionId, "2030-12-12", de, vol)
            )
        }
        it("Success") {
            assertEquals(
                    ExitCodes.SUCCESS,
                    accService.accounting(userId, permissionId, ds, de, vol)
            )
        }
    }
})
