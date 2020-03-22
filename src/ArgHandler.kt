import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

class ArgHandler(args: Array<String>) {

    private val parser = ArgParser("app.jar")

    //var h: Boolean = false
    val login: String? by parser
            .option(ArgType.String, shortName = "login")

    val pass: String? by parser
            .option(ArgType.String, shortName = "pass")

    val res: String by parser
            .option(ArgType.String, shortName = "res")
            .default("")

    val role: String by parser
            .option(ArgType.String, shortName = "role")
            .default("")

    val ds: String by parser
            .option(ArgType.String, shortName = "ds")
            .default("")

    val de: String by parser
            .option(ArgType.String, shortName = "de")
            .default("")

    val vol: String by parser
            .option(ArgType.String, shortName = "vol")
            .default("") //will convert to int in BusinessLogic

    init {
        try {
            parser.parse(args)
        } catch (e: IllegalStateException) {
            println(e.message)
            // exitProcess(0) //пройдут тесты в первоначальном варианте
        }
    }

    fun isAuthenticationRequired(): Boolean =
            !login.isNullOrEmpty() && !pass.isNullOrEmpty()

    fun isAuthorizationRequired(): Boolean =
            res.isNotEmpty() && role.isNotEmpty()

    fun isAccountingRequired(): Boolean =
            ds.isNotEmpty() && de.isNotEmpty() && vol.isNotEmpty()
}
