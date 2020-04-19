package com.dinosaur.app

import com.dinosaur.app.service.DBService
import kotlin.system.exitProcess
import org.apache.logging.log4j.kotlin.logger

fun main(args: Array<String>) {

    val log = logger("App")
    log.info("Program starts!")

    val dbService = DBService(log)
    var exitCode = 1
    val app = App()

    try {
        // 'use' instead of try/finally
        val argHandler = ArgHandler(args)
        // 'use' instead of try/finally
        dbService.use {
            it.getConnection() // we shouldn't connect to DB if not auth required TODO
            exitCode = app.run(argHandler, it.connection!!, log)
        }
    } catch (e: Exception) {
        log.error(e)
    } finally {
        exitProcess(exitCode)
    }
}
