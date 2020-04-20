package com.dinosaur.app.service

import com.dinosaur.app.ExitCodes
import com.dinosaur.app.Role
import com.dinosaur.app.dao.DAOAuthorization
import com.dinosaur.app.domain.Permission

class AuthorizationService(
    private val daoAuthorization: DAOAuthorization
) {

    var permission: Permission? = null

    fun authorization(
        login: String,
        resPath: String,
        role: String
    ): ExitCodes {
        if (!Role.isRoleExists(role)) return ExitCodes.INVALID_ROLE

        val permissions = daoAuthorization
                .confirmPermission(login, role)
                ?: return ExitCodes.ACCESS_DENIED

        for (perm in permissions) {
            if (resPath.isChild(perm.resPath)) {
                permission = perm
                return ExitCodes.SUCCESS
            }
        }
        return ExitCodes.ACCESS_DENIED
    }

    private fun String.isChild(pathFromDB: String): Boolean {

        // делим по точке желаемый ресурс и ресурс из коллекции
        val query: Array<String> = split(".").toTypedArray()
        val resFromDB: Array<String> = pathFromDB.split(".").toTypedArray()

        // если запрос короче чем ресурс из бд, то это не потомок
        if (query.size < resFromDB.size) {
            return false
        // иначе проверяем совпадение узлов по порядку (от 0 до длины ресурса из бд)
        } else {

            for (i in resFromDB.indices) {
                if (resFromDB[i] != query[i])
                    return false
            }
            return true
        }
    }
}
