package functional

import com.dinosaur.app.App
import org.spekframework.spek2.Spek
import kotlin.test.assertEquals

object AppTest: Spek ({
    group("Help") {
        val args: List<Array<String>> = arrayOf(
                "-h", "", "-q", "12345", "-res A.B 12345"
        ).map{ it.split(" ").toTypedArray() }

        println("Positive tests")
        test("-h") {
            assertEquals(0, App(args[0]).run())
        }

        println("Negative tests")
        test("Empty string") {
            assertEquals(1, App(args[1]).run())
        }
        test("-q") {
            assertEquals(1, App(args[2]).run())
        }
        test("12345") {
            assertEquals(1, App(args[3]).run())
        }
        test("-res A.B 12345") {
            assertEquals(1, App(args[4]).run())
        }
    }

    group("Authentication") {
        val args: List<Array<String>> = arrayOf(
                "-login vasya -pass 123",
                "-login VASYA -pass 123",
                "-login asd -pass 123",
                "-login admin -pass 123"
        ).map{ it.split(" ").toTypedArray() }

        println("Positive tests")
        test("-login vasya -pass 123") {
            assertEquals(0, App(args[0]).run())
        }

        println("Negative tests")
        test("Invalid login: VASYA") {
            assertEquals(2, App(args[1]).run())
        }
        test("Login not exists: asd") {
            assertEquals(3, App(args[2]).run())
        }
        test("Wrong password") {
            assertEquals(4, App(args[3]).run())
        }
    }

    group("Authorization") {
        val args: List<Array<String>> = arrayOf(
                "-login vasya -pass 123 -res A -role READ",
                "-login vasya -pass 123 -res A.B -role READ",
                "-login vasya -pass 123 -res A -role DELETE",
                "-login vasya -pass 123 -res A -role WRITE",
                "-login vasya -pass 0000 -res A.B -role DELETE",
                "-login admin -pass admin -res A -role EXECUTE",
                "-login admin -pass admin -res A.A -role WRITE"
                
        ).map{ it.split(" ").toTypedArray() }

        println("Positive tests")
        test("-login vasya -pass 123 -res A -role READ") {
            assertEquals(0, App(args[0]).run())
        }
        test("-login vasya -pass 123 -res A.B -role READ") {
            assertEquals(0, App(args[1]).run())
        }

        println("Negative tests")
        test("Invalid role: DELETE") {
            assertEquals(5, App(args[2]).run())
        }
        test("Access denied") {
            assertEquals(6, App(args[3]).run())
        }
        test("Wrong password") {
            assertEquals(4, App(args[4]).run())
        }
        test("Access denied") {
            assertEquals(6, App(args[5]).run())
        }
        test("Access denied") {
            assertEquals(6, App(args[6]).run())
        }
    }
    
    group("Accounting") {
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
        ).map{ it.split(" ").toTypedArray() }

        println("Positive tests")
        test("-login vasya -pass 123 -res A -role READ" +
                " -ds 2020-03-10 -de 2020-03-11 -vol 100") {
            assertEquals(0, App(args[0]).run())
        }

        println("Negative tests")
        test("Invalid activity") {
            assertEquals(7, App(args[1]).run())
        }
        test("Invalid activity") {
            assertEquals(7, App(args[2]).run())
        }
        test("Access denied") {
            assertEquals(6, App(args[3]).run())
        }
        test("Invalid activity") {
            assertEquals(7, App(args[3]).run())
        }
    }
})