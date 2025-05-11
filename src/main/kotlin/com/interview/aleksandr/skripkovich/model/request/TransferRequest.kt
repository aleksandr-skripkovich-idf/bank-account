package com.interview.aleksandr.skripkovich.model.request

import java.math.BigDecimal

data class TransferRequest(
    val toUserId: Long,
    val amount: BigDecimal,
)
