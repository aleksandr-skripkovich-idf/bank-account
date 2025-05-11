package com.interview.aleksandr.skripkovich.service

import reactor.core.publisher.Mono
import java.math.BigDecimal

interface TransferBalanceService {
    fun transfer(fromUserId: Long, toUserId: Long, amount: BigDecimal): Mono<String>
}