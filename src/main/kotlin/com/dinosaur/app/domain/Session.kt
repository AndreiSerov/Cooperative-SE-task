package com.dinosaur.app.domain

import java.time.LocalDate

data class Session(
        val user_id: Int,
        val permission_id: Int,
        val ds: LocalDate,
        val de: LocalDate,
        val vol: Int
)