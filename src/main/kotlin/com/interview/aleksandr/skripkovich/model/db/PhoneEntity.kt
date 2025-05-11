package com.interview.aleksandr.skripkovich.model.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("phone_data")
data class PhoneEntity(
    @Id
    val id: Long? = null,
    val userId: Long,
    val phone: String,
)
