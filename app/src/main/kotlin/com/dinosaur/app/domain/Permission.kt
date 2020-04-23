package com.dinosaur.app.domain

data class Permission(
    val id: Int,
    val login: String,
    val resPath: String,
    val role: String
)
