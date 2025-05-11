package com.interview.aleksandr.skripkovich.model.response

import java.time.LocalDate

data class SearchResponse(
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: LocalDate
)
