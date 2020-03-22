import kotlin.random.Random
import kotlin.system.exitProcess

// global value!!
val users: List<User> = listOf(
        User("vasya", "123", "89081"),
        User("admin", "admin", "92867"),
        User("q", "?!#", "41290"),
        User("abcdefghij", "pass", "10216")
)

val permissions: List<Permission> = listOf(
        Permission("A", "READ", "vasya"),
        Permission("A.B.C", "WRITE", "vasya"),
        Permission("A.B", "EXECUTE", "admin"),
        Permission("A", "READ", "admin"),
        Permission("A.B", "WRITE", "admin"),
        Permission("A.B.C", "READ", "admin"),
        Permission("B", "EXECUTE", "q")
)

val roles: Set<String> = setOf(
        "READ",
        "WRITE",
        "EXECUTE"
)

val sessions: MutableList<Session> = mutableListOf()


fun main(args: Array<String>) {

    val argHandler = ArgHandler(args)
    val businessLogic = BusinessLogic()

//    if (argHandler.isHelpRequired()) {
//        businessLogic.printHelp()
//    }

    val user: User = (
            if (argHandler.isAuthenticationRequired()) {
                businessLogic.authentication(argHandler.login!!, argHandler.pass!!)
            } else {
                exitProcess(1)
            })

    /*if(!businessLogic.isRoleExists(argHandler.role)){
        exitProcess(5) TODO
    }*/

    val permission: Permission = (
            if (argHandler.isAuthorizationRequired()) {
                businessLogic.authorization(argHandler.res, argHandler.role, user.login) ?: exitProcess(6)
            } else {
                exitProcess(0) //0 так как аутентификация прошла успешно
            })

    val session: Session = (
            if (argHandler.isAccountingRequired()) {
                businessLogic.accounting(permission, argHandler.ds, argHandler.de, argHandler.vol) ?: exitProcess(7)
            } else {
                exitProcess(0)
            })

    sessions.add(session)
    exitProcess(0)
}
