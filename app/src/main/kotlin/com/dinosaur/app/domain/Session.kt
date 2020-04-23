package com.dinosaur.app.domain

import java.time.LocalDate

data class Session(
    val userId: Int,
    val permissionId: Int,
    val ds: LocalDate,
    val de: LocalDate,
    val vol: Int
)
