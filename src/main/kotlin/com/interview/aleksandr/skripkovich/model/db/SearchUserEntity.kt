package com.interview.aleksandr.skripkovich.model.db

import java.time.LocalDate

data class SearchUserEntity(
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: LocalDate
)
