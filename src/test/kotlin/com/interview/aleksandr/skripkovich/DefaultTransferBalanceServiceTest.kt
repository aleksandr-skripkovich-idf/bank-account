package com.interview.aleksandr.skripkovich

import com.interview.aleksandr.skripkovich.model.db.AccountEntity
import com.interview.aleksandr.skripkovich.repository.AccountRepository
import com.interview.aleksandr.skripkovich.service.DefaultTransferBalanceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class DefaultTransferBalanceServiceTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var databaseClient: DatabaseClient


    private lateinit var transferBalanceService: DefaultTransferBalanceService

    @BeforeEach
    fun setUp() {
        transferBalanceService = DefaultTransferBalanceService(accountRepository, databaseClient)
    }

    @Test
    fun `should return error when transferring to self`() {
        val result = transferBalanceService.transfer(1L, 1L, BigDecimal.TEN)

        StepVerifier.create(result)
            .expectErrorMatches { it is IllegalArgumentException && it.message == "Cannot transfer to self" }
            .verify()
    }

    @Test
    fun `should return error when amount is not positive`() {
        val result = transferBalanceService.transfer(1L, 2L, BigDecimal.ZERO)

        StepVerifier.create(result)
            .expectErrorMatches { it is IllegalArgumentException && it.message == "Amount must be positive" }
            .verify()
    }

    @Test
    fun `should return error when insufficient funds in processTransfer`() {
        val from = AccountEntity(userId = 1L, balance = BigDecimal(10), startDeposit = BigDecimal(100))
        val to = AccountEntity(userId = 2L, balance = BigDecimal.ZERO, startDeposit = BigDecimal(50))

        whenever(accountRepository.findByUserIdForUpdate(1L)).thenReturn(Mono.just(from))
        whenever(accountRepository.findByUserIdForUpdate(2L)).thenReturn(Mono.just(to))

        StepVerifier.create(transferBalanceService.processTransfer(1L, 2L, BigDecimal(100)))
            .expectErrorMatches { it is IllegalStateException && it.message == "Insufficient funds" }
            .verify()
    }

    @Test
    fun `should transfer successfully in processTransfer`() {
        val from = AccountEntity(userId = 1L, balance = BigDecimal(100), startDeposit = BigDecimal(100))
        val to = AccountEntity(userId = 2L, balance = BigDecimal(50), startDeposit = BigDecimal(50))

        val updatedFrom = from.copy(balance = BigDecimal(90))
        val updatedTo = to.copy(balance = BigDecimal(60))

        whenever(accountRepository.findByUserIdForUpdate(1L)).thenReturn(Mono.just(from))
        whenever(accountRepository.findByUserIdForUpdate(2L)).thenReturn(Mono.just(to))
        whenever(accountRepository.save(updatedFrom)).thenReturn(Mono.just(updatedFrom))
        whenever(accountRepository.save(updatedTo)).thenReturn(Mono.just(updatedTo))

        StepVerifier.create(transferBalanceService.processTransfer(1L, 2L, BigDecimal(10)))
            .expectNext("Transfer successful")
            .verifyComplete()
    }
}