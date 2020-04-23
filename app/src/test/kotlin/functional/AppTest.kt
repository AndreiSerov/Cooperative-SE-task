package com.dinosaur.app

import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AppTest : Spek({

    describe("Help") {
        val args: List<Array<String>> = arrayOf(
                "-h", "", "-q", "12345", "-res A.B 12345"
        ).map { it.split(" ").toTypedArray() }

//        println("Positive tests")
//        it("-h") {
//            assertEquals(0, App().run(args[0]))
//        }

        println("Negative tests")
        it("Empty string") {
            assertEquals(1, App().run(args[1]))
        }
        it("-q") {
            assertEquals(1, App().run(args[2]))
        }
        it("12345") {
            assertEquals(1, App().run(args[3]))
        }
        it("-res A.B 12345") {
            assertEquals(1, App().run(args[4]))
        }
    }

    describe("Authentication") {
        val args: List<Array<String>> = arrayOf(
                "-login vasya -pass 123",
                "-login VASYA -pass 123",
                "-login asd -pass 123",
                "-login admin -pass 123"
        ).map { it.split(" ").toTypedArray() }

        println("Positive tests")
        it("-login vasya -pass 123") {
            assertEquals(0, App().run(args[0]))
        }

        println("Negative tests")
        it("Invalid login: VASYA") {
            assertEquals(2, App().run(args[1]))
        }
        it("Login not exists: asd") {
            assertEquals(3, App().run(args[2]))
        }
        it("Wrong password") {
            assertEquals(4, App().run(args[3]))
        }
    }

    describe("Authorization") {
        val args: List<Array<String>> = arrayOf(
                "-login vasya -pass 123 -res A -role READ",
                "-login vasya -pass 123 -res A.B -role READ",
                "-login vasya -pass 123 -res A -role DELETE",
                "-login vasya -pass 123 -res A -role WRITE",
                "-login vasya -pass 0000 -res A.B -role DELETE",
                "-login admin -pass admin -res A -role EXECUTE",
                "-login admin -pass admin -res A.A -role WRITE"

        ).map { it.split(" ").toTypedArray() }

        println("Positive tests")
        it("-login vasya -pass 123 -res A -role READ") {
            assertEquals(0, App().run(args[0]))
        }
        it("-login vasya -pass 123 -res A.B -role READ") {
            assertEquals(0, App().run(args[1]))
        }

        println("Negative tests")
        it("Invalid role: DELETE") {
            assertEquals(5, App().run(args[2]))
        }
        it("Access denied") {
            assertEquals(6, App().run(args[3]))
        }
        it("Wrong password") {
            assertEquals(4, App().run(args[4]))
        }
        it("Access denied") {
            assertEquals(6, App().run(args[5]))
        }
        it("Access denied") {
            assertEquals(6, App().run(args[6]))
        }
    }

    describe("Accounting") {
        val args: List<Array<String>> = arrayOf(
                "-login vasya -pass 123 -res A -role READ" +
                        " -ds 2020-03-10 -de 2020-03-11 -vol 100",
                "-login vasya -pass 123 -res A -role READ" +
                        " -ds 2020.03.10 -de 2020.03.11 -vol 100",
                "-login vasya -pass 123 -res A -role READ" +
                        " -ds 2020-03-10 -de 2020-03-11 -vol aaaa",
                "-login vasya -pass 123 -res A -role EXECUTE" +
                        " -ds 01.02.3012 -de 01.02.2030 -vol aaa",
                "-login vasya -pass 123 -res A -role READ" +
                        " -ds 2020-03-11 -de 2020-03-10 -vol 100"
        ).map { it.split(" ").toTypedArray() }

        println("Positive tests")
        it("-login vasya -pass 123 -res A -role READ" +
                " -ds 2020-03-10 -de 2020-03-11 -vol 100") {
            assertEquals(0, App().run(args[0]))
        }

        println("Negative tests")
        it("Invalid activity") {
            assertEquals(7, App().run(args[1]))
        }
        it("Invalid activity") {
            assertEquals(7, App().run(args[2]))
        }
        it("Access denied") {
            assertEquals(6, App().run(args[3]))
        }
        it("Invalid activity") {
            assertEquals(7, App().run(args[4]))
        }
    }
})
