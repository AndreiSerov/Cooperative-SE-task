package com.dinosaur.app

import com.dinosaur.app.dao.DAOAccounting
import com.dinosaur.app.dao.DAOAuthentication
import com.dinosaur.app.dao.DAOAuthorization
import com.dinosaur.app.domain.Permission
import com.dinosaur.app.service.AccountingService
import com.dinosaur.app.service.AuthenticationService
import com.dinosaur.app.service.AuthorizationService
import com.dinosaur.app.service.DBService
import org.apache.logging.log4j.kotlin.logger

class App() {
    private val log = logger("App")
    private val dbService = DBService(log)

    fun run(args: Array<String>): Int {
        log.info("Program starts!")
        val argHandler = ArgHandler(args)
        // if run with any wrong arg. Program wouldn't come below
        // 'use' instead of try/finally
        try {
            if (!argHandler.isLoginValid(argHandler.login!!)) {
                return ExitCodes.INVALID_LOGIN.code
            }
        } catch (e: KotlinNullPointerException) {
            return ExitCodes.HELP.code
        }

        dbService.use {
            it.getConnection()
            val connection = dbService.connection!!

            // we should exit DB session in any case
            val daoAuthentication = DAOAuthentication(connection) // not null
            val authenticationService = AuthenticationService(daoAuthentication)

            // Authentication
            var exitCode: ExitCodes = if (argHandler.isAuthenticationRequired()) {
                authenticationService.authentication(argHandler.login!!, argHandler.pass!!)
            } else {
                log.info("Printing help")
                ExitCodes.HELP
            }

            if (exitCode != ExitCodes.SUCCESS) {
                log.warn("Access to ${argHandler.login} denied. " + "Check that login and password correct")
                return exitCode.code
            }

            // если user null, программа завершится раньше
            val user = authenticationService.user!!

            // Authorization
            // if authentication passed create instance of AuthorizationService
            val daoAuthorization = DAOAuthorization(connection) // not null
            val authorizationService = AuthorizationService(daoAuthorization)
            exitCode = if (argHandler.isAuthorizationRequired()) {
                authorizationService.authorization(
                        user.login, argHandler.res, argHandler.role
                )
            } else {
                // 0 так как аутентификация прошла успешно
                log.info("Hello, ${argHandler.login}!") // Welcome User
                return ExitCodes.SUCCESS.code
            }

            if (exitCode != ExitCodes.SUCCESS) {
                log.warn("Access with parameters:\n" +
                        "\tres: ${argHandler.res}\n" +
                        "\trole: ${argHandler.role}\n" +
                        "\tuser: ${user.login} denied.\n" +
                        "Check that parameters correct"
                )
                return exitCode.code
            }

            // if permission is null program wouldn't reach code below
            val permission: Permission = authorizationService.permission!!

            // Accounting
            // if authorization passed create instance of AccountingService
            val daoAccounting = DAOAccounting(connection)
            val accountingService = AccountingService(daoAccounting)
            exitCode = if (argHandler.isAccountingRequired()) {
                accountingService.accounting(
                        user.id,
                        permission.id,
                        argHandler.ds,
                        argHandler.de,
                        argHandler.vol
                )
            } else {
                log.warn("Success accounting with parameters:\n" +
                        "\tStart Date: ${argHandler.ds}\n" +
                        "\tEnd Date: ${argHandler.de} denied.\n" +
                        "\tVolume: ${argHandler.vol} denied.\n"
                )
                return ExitCodes.SUCCESS.code
            }

            if (exitCode != ExitCodes.SUCCESS) {
                log.warn("Access with parameters: \n" +
                        "\tStart Date: ${argHandler.ds}\n" +
                        "\tEnd Date: ${argHandler.de} denied.\n" +
                        "\tVolume: ${argHandler.vol} denied.\n" +
                        "Check that parameters correct"
                )
                return exitCode.code
            }

            log.info("Accounting data stored")
            return exitCode.code
        }
    }
}
