package com.interview.aleksandr.skripkovich.controller

import com.interview.aleksandr.skripkovich.model.SearchUser
import com.interview.aleksandr.skripkovich.model.request.LoginRequest
import com.interview.aleksandr.skripkovich.model.request.TransferRequest
import com.interview.aleksandr.skripkovich.model.response.SearchResponse
import com.interview.aleksandr.skripkovich.service.CookiesUtil
import com.interview.aleksandr.skripkovich.service.JwtUtil
import com.interview.aleksandr.skripkovich.service.TransferBalanceService
import com.interview.aleksandr.skripkovich.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val transferBalanceService: TransferBalanceService,
    private val jwtUtil: JwtUtil,
    private val cookiesUtil: CookiesUtil,
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest, response: HttpServletResponse): Mono<Boolean> {
        return userService.authentication(
            login = loginRequest.login,
            password = loginRequest.password
        )
            .filter { data -> data.success }
            .doOnNext { data ->
                val token = jwtUtil.generateToken(data.userId!!)
                cookiesUtil.setJwt(token, response)
            }
            .map { data -> data.success }
            .defaultIfEmpty(false)
            .doOnSubscribe { logger.info("Start login") }
            .doOnSuccess { logger.info("Finish login") }
    }

    @GetMapping
    fun searchUsers(
        @RequestParam(required = false) dateOfBirth: LocalDate?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Flux<SearchResponse> {
        return userService.searchUsers(
            SearchUser(
                dateOfBirth = dateOfBirth,
                phone = phone,
                name = name,
                email = email,
                page = page,
                size = size,
            )
        )
            .doOnError { e -> logger.error("Error search", e) }
    }

    @PostMapping("/transfer")
    fun transfer(
        @RequestBody transferRequest: TransferRequest,
        request: HttpServletRequest
    ): Mono<ResponseEntity<String>> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { fromUserId ->
                transferBalanceService.transfer(fromUserId, transferRequest.toUserId, transferRequest.amount)
            }
            .map { result -> ResponseEntity.ok(result) }
            .onErrorResume { e -> Mono.just(ResponseEntity.badRequest().body(e.message ?: "Error")) }
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }
}