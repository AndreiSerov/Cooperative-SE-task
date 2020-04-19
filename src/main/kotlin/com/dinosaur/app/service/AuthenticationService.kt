package com.dinosaur.app.service

import com.dinosaur.app.ExitCodes
import com.dinosaur.app.dao.DAOAuthentication
import com.dinosaur.app.domain.User
import java.security.MessageDigest

class AuthenticationService(private val daoAuthentication: DAOAuthentication) {

    var user: User? = null

    fun authentication(login: String, pass: String): ExitCodes {
        // check that user exists in db
        user = daoAuthentication.getUserData(login)
                ?: return ExitCodes.USER_NOT_FOUND

        if (!checkPassword(pass, user!!)) {
            return ExitCodes.WRONG_PASSWORD
        }
        return ExitCodes.SUCCESS
    }

    private fun checkPassword(pass: String, user: User) =
            pass.getHash(user.salt) == user.hash

    // stackoverflow driven development
    private fun String.getHash(
        salt: String,
        algorithm: String = "SHA-512"
    ): String {
        val bytes = (salt + this).toByteArray()
        val md = MessageDigest.getInstance(algorithm)
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}
