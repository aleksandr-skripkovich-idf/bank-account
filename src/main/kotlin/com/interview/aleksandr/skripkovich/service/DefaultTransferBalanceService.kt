package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.repository.AccountRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import java.math.BigDecimal

@Service
class DefaultTransferBalanceService(
    private val accountRepository: AccountRepository,
    private val databaseClient: DatabaseClient
) : TransferBalanceService {

    override fun transfer(fromUserId: Long, toUserId: Long, amount: BigDecimal): Mono<String> {
        if (amount <= BigDecimal.ZERO) return Mono.error(IllegalArgumentException("Amount must be positive"))
        if (fromUserId == toUserId) return Mono.error(IllegalArgumentException("Cannot transfer to self"))

        return databaseClient.inConnection { processTransfer(fromUserId, toUserId, amount) }
    }

    internal fun processTransfer(fromUserId: Long, toUserId: Long, amount: BigDecimal): Mono<String> {
        return Mono.zip(
            accountRepository.findByUserIdForUpdate(fromUserId),
            accountRepository.findByUserIdForUpdate(toUserId)
        ).flatMap { (fromAccount, toAccount) ->
            if (fromAccount.balance < amount) {
                return@flatMap Mono.error(IllegalStateException("Insufficient funds"))
            }

            val updatedFrom = fromAccount.copy(balance = fromAccount.balance - amount)
            val updatedTo = toAccount.copy(balance = toAccount.balance + amount)

            accountRepository.save(updatedFrom)
                .then(accountRepository.save(updatedTo))
                .thenReturn("Transfer successful")
        }
    }
}
