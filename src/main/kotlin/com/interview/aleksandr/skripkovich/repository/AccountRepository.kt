package com.interview.aleksandr.skripkovich.repository

import com.interview.aleksandr.skripkovich.model.db.AccountEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AccountRepository : R2dbcRepository<AccountEntity, Long> {

    @Query(
        """
        UPDATE account
        SET balance = LEAST(balance * 1.10, start_deposit * 2.07)
        WHERE balance < start_deposit * 2.07
        """
    )
    fun increaseBalance(): Mono<Int>

    @Query("SELECT * FROM account WHERE user_id = :userId FOR UPDATE")
    fun findByUserIdForUpdate(userId: Long): Mono<AccountEntity>
}