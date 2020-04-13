package com.dinosaur.app

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default


class ArgHandler(args: Array<String>) {

    private val parser = ArgParser(
            "app.jar",
            true,
            ArgParser.OptionPrefixStyle.LINUX,
            false
    )

    val login: String? by parser
            .option(ArgType.String, null, "login", null, null)

    val pass: String? by parser
            .option(ArgType.String, null, "pass", null, null)

    val res: String by parser
            .option(ArgType.String, null, "res", null, null)
            .default("")

    val role: String by parser
            .option(ArgType.String, null, "role", null, null)
            .default("")

    val ds: String by parser
            .option(ArgType.String, null, "ds", null, null)
            .default("")

    val de: String by parser
            .option(ArgType.String, null, "de", null, null)
            .default("")

    val vol: String by parser
            .option(ArgType.String, null, "vol", null, null)
            .default("") //will convert to int in BusinessLogic

    init {
        try {
            parser.parse(args)
        } catch (e: IllegalStateException) {
            println(e.message)
        }
    }

    fun checkArgs(): Int {
        val exitCode = when {
            // return code 2
            !isLoginValid(login!!) -> ExitCodes.INVALID_LOGIN
            // return code 5 if role not exists
            !Role.isRoleExists(role) && role.isNotBlank() ->
                ExitCodes.INVALID_ROLE
            else -> ExitCodes.HELP
        }
        return exitCode.code
    }

    fun isAuthenticationRequired(): Boolean =
            !login.isNullOrEmpty() && !pass.isNullOrEmpty()

    fun isAuthorizationRequired(): Boolean =
            res.isNotEmpty() && role.isNotEmpty()

    fun isAccountingRequired(): Boolean =
            ds.isNotEmpty() && de.isNotEmpty() && vol.isNotEmpty()

    fun isLoginValid(login: String): Boolean
            = "^[a-z]{1,10}$".toRegex().matches(login)
}
