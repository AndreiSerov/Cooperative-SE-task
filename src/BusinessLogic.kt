import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.system.exitProcess

class BusinessLogic {

    private fun isLoginValid(login: String): Boolean {
        val pattern = "^[a-z]{1,10}$".toRegex()
        return pattern.matches(login)
    }

    private fun findUser(login: String, users: List<User>): User? {
        for (user in users) {
            if (user.login == login) {
                return user
            }
        }
        return null
    }

    private fun checkPassword(pass: String, user: User): Boolean {
        return pass.getHash(user.salt) == user.hash
    }

    //stackoverflow driven development
    private fun String.getHash(salt: String, algorithm: String = "SHA-512"): String {
        val bytes = (salt + this).toByteArray()
        val md = MessageDigest.getInstance(algorithm)
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun authentication(login: String, pass: String): User {
        if (!isLoginValid(login)) {
            exitProcess(2)
        }
        // check that user exists in db
        val user = findUser(login, users) ?: exitProcess(3)

        if (!checkPassword(pass, user)) {
            exitProcess(4)
        }

        return user
    }

    private fun isRoleExists(role: String): Boolean = roles.contains(role)

    fun authorization(resPath: String,
                      role: String,
                      username: String): Permission? {

        if (!isRoleExists(role)) exitProcess(5) //возвращаем код 5, если роль не существует
        //TODO
        //возможно, перенести в Main

        for (permission in permissions) {
            if (username == permission.username && role == permission.role) {
                if (isChild(resPath, permission.resPath)) {
                    return permission
                }
            }
        }
        return null
    }

    private fun isChild(pathFromQuery: String, pathFromDB: String): Boolean {

        //делим по точке желаемый ресурс и ресурс из коллекции
        val query: Array<String> = pathFromQuery.split(".").toTypedArray()
        val resFromDB: Array<String> = pathFromDB.split(".").toTypedArray()

        if (query.size < resFromDB.size) { //если запрос короче чем ресурс из бд, то это не потомок
            return false
        } else { //иначе проверяем совпадение узлов по порядку (от 0 до длины ресурса из бд)

            for (i in resFromDB.indices) {
                if (resFromDB[i] != query[i]) return false
            }
            return true
        }

    }

    fun accounting(res: Permission,
                   ds: String,
                   de: String,
                   vol: String): Session? {

        val volInt = vol.toInteger() ?: return null
        val dateStart = ds.toDate() ?: return null
        val dateEnd = de.toDate() ?: return null

        return if (dateEnd >= dateStart && volInt >= 0) {
            Session(res, dateStart, dateEnd, volInt)
        } else {
            null
        }

    }

    private fun String.toDate(pattern: String = "yyyy-MM-dd"): LocalDate? {
        return try {
            LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
        } catch (e: DateTimeParseException) { //если дата некорректная возвращаем null
            null
        }
    }

    private fun String.isDateCorrect(): Boolean {
        val pattern = "^\\d{4}-\\d{2}-\\d{2}$".toRegex()
        return this.matches(pattern)
    }

    private fun String.toInteger(): Int? {
        return try {
            this.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
}