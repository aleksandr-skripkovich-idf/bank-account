package com.interview.aleksandr.skripkovich.model

import java.time.LocalDate

data class SearchUser(
    val dateOfBirth: LocalDate?,
    val phone: String?,
    val name: String?,
    val email: String?,
    val page: Int,
    val size: Int
)
