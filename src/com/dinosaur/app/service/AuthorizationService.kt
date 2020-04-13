package com.dinosaur.app.service

import com.dinosaur.app.ExitCodes
import com.dinosaur.app.Role
import com.dinosaur.app.domain.Permission

class AuthorizationService(private val permissions: List<Permission>) {

    var permission: Permission? = null

    fun authorization(
            resPath: String,
            role: String,
            username: String
    ): ExitCodes {
        if (!Role.isRoleExists(role)) return ExitCodes.INVALID_ROLE


        for (perm in permissions) {
            if (username != perm.username || role != perm.role) {
                continue
            }
            if (isChild(resPath, perm.resPath)) {
                permission = perm
                return ExitCodes.SUCCESS
            }
        }
        return ExitCodes.ACCESS_DENIED
    }

    private fun isChild(pathFromQuery: String, pathFromDB: String): Boolean {

        //делим по точке желаемый ресурс и ресурс из коллекции
        val query: Array<String> = pathFromQuery.split(".").toTypedArray()
        val resFromDB: Array<String> = pathFromDB.split(".").toTypedArray()

        //если запрос короче чем ресурс из бд, то это не потомок
        if (query.size < resFromDB.size) {
            return false
        //иначе проверяем совпадение узлов по порядку (от 0 до длины ресурса из бд)
        } else {

            for (i in resFromDB.indices) {
                if (resFromDB[i] != query[i]) return false
            }
            return true
        }
    }
}