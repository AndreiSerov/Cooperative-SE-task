package com.dinosaur.app

import com.dinosaur.app.dao.DAOAuthentication
import com.dinosaur.app.domain.User
import com.dinosaur.app.service.AuthenticationService
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AuthenticationTest : Spek({
    describe("Authentication") {
        val user = User(
                1,
                "admin",
                "26b6b57d98e7650c5071c3f4bf630e2051d6a25efac183a6bab213dc9bc31b600ab29695e76f9df7ca2a7ddf05ef9ef01078804f49a9c17b5c3441e62e569cb9",
                "92867"
        )
        val login = "admin"
        val daoAuthentication = mockk<DAOAuthentication>()
        every { daoAuthentication.getUserData(login) } returns user
        val authService = AuthenticationService(daoAuthentication)

        it("User not found") {
            every { daoAuthentication.getUserData("123") } returns null
            assertEquals(
                    ExitCodes.USER_NOT_FOUND,
                    authService.authentication("123", "123")
            )
        }
        it("Wrong password") {
            assertEquals(
                    ExitCodes.WRONG_PASSWORD,
                    authService.authentication(login, "123")
            )
        }
        it("Success") {
            assertEquals(
                    ExitCodes.SUCCESS,
                    authService.authentication(login, "admin")
            )
        }
    }
})
