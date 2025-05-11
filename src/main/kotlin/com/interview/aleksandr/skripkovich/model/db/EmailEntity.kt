package com.interview.aleksandr.skripkovich.model.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("email_data")
data class EmailEntity(
    @Id
    val id: Long? = null,
    val userId: Long,
    val email: String,
)
