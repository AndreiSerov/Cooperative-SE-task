package com.dinosaur.app

import com.dinosaur.app.dao.DAOAuthentication
import com.dinosaur.app.domain.Permission
import com.dinosaur.app.service.AccountingService
import com.dinosaur.app.service.AuthenticationService
import com.dinosaur.app.service.AuthorizationService
import com.dinosaur.app.service.DBService
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import java.sql.Connection
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val log = logger("App")
    log.info("Program starts!")

    val dbService = DBService(log)
    var exitCode = 0

    try {
        // use instead of try/finally
        dbService.use {
            it.getConnection()
            exitCode = run(args, dbService.connection, log)
        }
    } catch (e: Exception) {
        log.error(e)
    }

    exitProcess(exitCode)
}

fun run(args: Array<String>,
        connection: Connection?,
        log: KotlinLogger): Int {
    // we should exit DB session in any case
    val daoAuthentication = DAOAuthentication(connection!!) // not null
    val argHandler = ArgHandler(args)
    val authenticationService = AuthenticationService(daoAuthentication)

    // Authentication
    var exitCode: ExitCodes = if (argHandler.isAuthenticationRequired()) {
        authenticationService.authentication(
                argHandler.login!!,
                argHandler.pass!!
        )
    } else {
        log.info("Printing help")
        ExitCodes.HELP
    }

    if (exitCode != ExitCodes.SUCCESS) {
        log.warn("Access to ${argHandler.login} denied. " +
                "Check that login and password correct"
        )
        return exitCode.code
    }

    //если user null, программа завершится раньше
    val user = authenticationService.user!!

    // Authorization
    // if authentication passed create instance of AuthorizationService
    val authorizationService = AuthorizationService(permissions)
    exitCode = if (argHandler.isAuthorizationRequired()) {
        authorizationService.authorization(
                argHandler.res, argHandler.role, user.login
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

    val permission: Permission = authorizationService.permission!!

    // Accounting
    // if authorization passed create instance of AccountingService
    val accountingService = AccountingService(sessions)
    exitCode = if (argHandler.isAccountingRequired()) {
        accountingService.accounting(
                permission,
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

    sessions.add(accountingService.session!!)
    log.info("Accouting data stored")
    return exitCode.code
}