package com.interview.aleksandr.skripkovich.model.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("user")
data class UserEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val dateOfBirth: LocalDate,
    val password: String,
)
