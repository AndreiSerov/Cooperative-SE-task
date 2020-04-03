package com.dinosaur.app

import com.dinosaur.app.domain.Permission
import com.dinosaur.app.service.AccountingService
import com.dinosaur.app.service.AuthenticationService
import com.dinosaur.app.service.AuthorizationService
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val argHandler = ArgHandler(args)
    val authenticationService = AuthenticationService(users)

    var exitCode: ExitCodes = if (argHandler.isAuthenticationRequired()) {
        authenticationService.authentication(argHandler.login!!, argHandler.pass!!)
    } else {
        ExitCodes.HELP
    }
    if (exitCode != ExitCodes.SUCCESS) exitProcess(exitCode.code)

    //если user null, программа завершится раньше
    val user = authenticationService.user!!

    // if authentication passed create instance of AuthorizationService
    val authorizationService = AuthorizationService(permissions)
    exitCode = if (argHandler.isAuthorizationRequired()) {
        authorizationService.authorization(argHandler.res, argHandler.role, user.login)
    } else {
        //0 так как аутентификация прошла успешно
        exitProcess(ExitCodes.SUCCESS.code)
    }

    if (exitCode != ExitCodes.SUCCESS) exitProcess(exitCode.code)

    val permission: Permission = authorizationService.permission!!

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
        exitProcess(ExitCodes.SUCCESS.code)
    }

    if (exitCode != ExitCodes.SUCCESS) exitProcess(exitCode.code)

    sessions.add(accountingService.session!!)
    exitProcess(exitCode.code)
}
