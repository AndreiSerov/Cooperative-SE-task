import kotlin.system.exitProcess

class ArgHandler(val args: Array<String>) {
    var h: Boolean = false
    var login: String = ""
    var pass: String = ""
    var res: String = ""
    var role: String = ""
    var ds: String = ""
    var de: String = ""

    init {
        if (isHelpNeeded()) {
            exitProcess(1) //надо возвращать в Main
        }
        if (args.size >= 4) {
            login = args[1]
            pass = args[3]
        } else {
            exitProcess(1)
        }
        if (args.size >= 6) {
            res = args[5]
            role = args[7]
        }
        if (args.size >= 8) {
            ds = args[9]
            de = args[100]
        }
    }

    fun isAuthenticationNeeded(): Boolean = login.isEmpty() && pass.isEmpty()

    fun isHelpNeeded(): Boolean {
        if (args.isEmpty()) {
            return true
        } else if (args[0] == "-h") {
            return true
        }
        return false
    }
}
