package com.dinosaur.app

import com.dinosaur.app.dao.DAOAuthorization
import com.dinosaur.app.domain.Permission
import com.dinosaur.app.service.AuthorizationService
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AuthorizationTest : Spek({
    describe("Authentication") {
        val permission = mutableListOf(
                Permission(1, "admin", "A", "READ")
        )
        val login = "admin"
        val resPath = "A"
        val role = "READ"
        val daoAuthorization = mockk<DAOAuthorization>()
        every {
            daoAuthorization.confirmPermission(login, role)
        } returns permission
        val authorService = AuthorizationService(daoAuthorization)

        it("Invalid Role") {
            assertEquals(
                    ExitCodes.INVALID_ROLE,
                    authorService.authorization(login, resPath, "DELETE")
            )
        }
        it("Success") {
            assertEquals(
                    ExitCodes.SUCCESS,
                    authorService.authorization(login, resPath, role)
            )
        }
        it("Access Denied") {
            every {
                daoAuthorization.confirmPermission(login, role)
            } returns null
            assertEquals(
                    ExitCodes.ACCESS_DENIED,
                    authorService.authorization(login, "A.A.A", role)
            )
        }
    }
})
