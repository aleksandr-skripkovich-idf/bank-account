package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class BalanceUpdateService(
    private val accountRepository: AccountRepository,
) {

    @Scheduled(fixedRate = 30000)
    fun updateBalances() {
        accountRepository.increaseBalance()
            .doOnNext { count -> logger.info("Updated rows: $count") }
            .subscribe()
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(BalanceUpdateService::class.java)
    }
}