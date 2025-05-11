package com.interview.aleksandr.skripkovich.model.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("account")
data class AccountEntity(
    @Id
    val id: Long? = null,
    val userId: Long,
    val balance: BigDecimal,
    val startDeposit: BigDecimal,
)
