package com.interview.aleksandr.skripkovich.controller

import com.interview.aleksandr.skripkovich.model.request.EmailCreateRequest
import com.interview.aleksandr.skripkovich.model.request.EmailDeleteRequest
import com.interview.aleksandr.skripkovich.model.request.EmailUpdateRequest
import com.interview.aleksandr.skripkovich.model.response.DefaultResponse
import com.interview.aleksandr.skripkovich.service.CookiesUtil
import com.interview.aleksandr.skripkovich.service.DataService
import com.interview.aleksandr.skripkovich.service.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/emails")
class EmailController(
    @Qualifier("emailDataService")
    private val emailDataService: DataService,
    private val jwtUtil: JwtUtil,
    private val cookiesUtil: CookiesUtil,
) {

    @PutMapping
    fun updateEmail(
        @Valid
        @RequestBody
        emailUpdateRequest: EmailUpdateRequest,
        request: HttpServletRequest,
    ): Mono<DefaultResponse> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { userId ->
                emailDataService.update(
                    userId,
                    emailUpdateRequest.newEmail,
                    emailUpdateRequest.oldEmail
                )
            }
            .doOnError { e -> logger.error("Error update email", e) }
    }

    @PostMapping
    fun saveEmail(
        @Valid
        @RequestBody
        emailCreateRequest: EmailCreateRequest,
        request: HttpServletRequest,
    ): Mono<DefaultResponse> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { userId ->
                emailDataService.create(
                    userId,
                    emailCreateRequest.email,
                )
            }
            .doOnError { e -> logger.error("Error create email", e) }
    }

    @DeleteMapping
    fun deleteEmail(
        @Valid
        @RequestBody
        emailDeleteRequest: EmailDeleteRequest,
        request: HttpServletRequest,
    ): Mono<DefaultResponse> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { userId ->
                emailDataService.delete(
                    userId,
                    emailDeleteRequest.email,
                )
            }
            .doOnError { e -> logger.error("Error delete email", e) }
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(EmailController::class.java)
    }
}