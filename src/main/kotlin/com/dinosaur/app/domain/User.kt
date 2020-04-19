package com.dinosaur.app.domain

data class User(
    val id: Int,
    val login: String,
    val hash: String,
    val salt: String
)
