import kotlin.system.exitProcess

//global collections
val users: List<User> = listOf(
        User(
                "vasya",
                "e5b0ab137b5a46379fd99d6068f87920b61488455df85d04c7737edf652264b40fe03049caf96ca7f63bb2f290f3145ce91e3cd699af0bfb6dac2b2b068c7950",
                "89081"),
        User(
                "admin",
                "26b6b57d98e7650c5071c3f4bf630e2051d6a25efac183a6bab213dc9bc31b600ab29695e76f9df7ca2a7ddf05ef9ef01078804f49a9c17b5c3441e62e569cb9",
                "92867"),
        User(
                "q",
                "97ed56b5fdd50b5b48132617783dc943d01bd1e9f31599d83ad85fd10df5cfb67819093bc334454c0cf0446087c331853e5d2511576509464c249a926bc52971",
                "41290"),
        User(
                "abcdefghij",
                "3de7d653fa01277e45de436d38902227f415918d36e124f649f7ef57820a7d8e5ad710ce58a8ab38e6d33e73a94f6562ee0c7e71d0fffe8eb3151137f631a94b",
                "10216")
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

    val user: User = (
            if (argHandler.isAuthenticationRequired()) {
                businessLogic.authentication(argHandler.login!!, argHandler.pass!!)
            } else {
                exitProcess(1)
            })

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
