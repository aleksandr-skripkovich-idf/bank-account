package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.AuthenticationData
import com.interview.aleksandr.skripkovich.model.Login
import com.interview.aleksandr.skripkovich.model.SearchUser
import com.interview.aleksandr.skripkovich.model.db.PasswordData
import com.interview.aleksandr.skripkovich.model.response.SearchResponse
import com.interview.aleksandr.skripkovich.repository.UserRepository
import com.interview.aleksandr.skripkovich.util.EmailUtil
import com.interview.aleksandr.skripkovich.util.PhoneUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

private const val PASSWORD_DATA_KEY = "PASSWORD_DATA:"

@Service
class DefaultUserService(
    @Qualifier("emailDataService")
    private val emailDataService: DataService,
    @Qualifier("phoneDataService")
    private val phoneDataService: DataService,
    private val userRepository: UserRepository,
    private val cacheService: CacheService,
) : UserService {

    override fun authentication(login: String, password: String): Mono<AuthenticationData> {
        return cacheService.getFromCache(PASSWORD_DATA_KEY + login)
            .switchIfEmpty {
                providePassword(login)
                    .delayUntil { cacheService.saveToCache(PASSWORD_DATA_KEY + login, it) }
            }
            .filter { data -> data.password == password }
            .map { data ->
                AuthenticationData(
                    userId = data.id,
                    success = true
                )
            }
            .defaultIfEmpty(AuthenticationData(success = false))
            .onErrorReturn(AuthenticationData(success = false))
            .doOnError { e -> logger.error("Error authentication", e) }
    }

    override fun searchUsers(searchUser: SearchUser): Flux<SearchResponse> {
        return userRepository.searchUsers(
            searchUser.dateOfBirth,
            searchUser.phone,
            searchUser.name,
            searchUser.email,
            searchUser.page,
            searchUser.size
        )
            .map { data ->
                SearchResponse(
                    name = data.name,
                    email = data.email,
                    phone = data.phone,
                    dateOfBirth = data.dateOfBirth
                )
            }
    }

    private fun providePassword(login: String): Mono<PasswordData> {
        return when (provideLogin(login)) {
            Login.EMAIL -> emailDataService.getAuthentication(login)
            Login.PHONE -> phoneDataService.getAuthentication(login)
            else -> Mono.empty()
        }
    }

    private fun provideLogin(login: String): Login {
        return if (login.contains(EmailUtil.EMAIL_REGEXP)) {
            Login.EMAIL
        } else if (login.contains(PhoneUtil.PHONE_REGEXP)) {
            Login.PHONE
        } else {
            Login.UNKNOWN
        }
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(DefaultUserService::class.java)
    }
}