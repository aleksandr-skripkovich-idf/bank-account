package com.interview.aleksandr.skripkovich.controller

import com.interview.aleksandr.skripkovich.model.request.PhoneCreateRequest
import com.interview.aleksandr.skripkovich.model.request.PhoneDeleteRequest
import com.interview.aleksandr.skripkovich.model.request.PhoneUpdateRequest
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
@RequestMapping("/phones")
class PhoneController(
    @Qualifier("phoneDataService")
    private val phoneDataService: DataService,
    private val jwtUtil: JwtUtil,
    private val cookiesUtil: CookiesUtil,
) {

    @PutMapping
    fun updateEmail(
        @Valid
        @RequestBody
        phoneUpdateRequest: PhoneUpdateRequest,
        request: HttpServletRequest
    ): Mono<DefaultResponse> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { userId ->
                phoneDataService.update(
                    userId,
                    phoneUpdateRequest.newPhone,
                    phoneUpdateRequest.oldPhone
                )
            }
            .doOnError { e -> logger.error("Error update phone", e) }
    }

    @PostMapping
    fun saveEmail(
        @Valid
        @RequestBody
        phoneCreateRequest: PhoneCreateRequest,
        request: HttpServletRequest
    ): Mono<DefaultResponse> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { userId ->
                phoneDataService.create(
                    userId,
                    phoneCreateRequest.phone,
                )
            }
            .doOnError { e -> logger.error("Error create phone", e) }
    }

    @DeleteMapping
    fun deleteEmail(
        @Valid
        @RequestBody
        phoneDeleteRequest: PhoneDeleteRequest,
        request: HttpServletRequest,
    ): Mono<DefaultResponse> {
        return Mono.fromCallable<String> { cookiesUtil.extractJwt(request) }
            .map { token -> jwtUtil.getUserId(token) }
            .flatMap { userId ->
                phoneDataService.delete(
                    userId,
                    phoneDeleteRequest.phone,
                )
            }
            .doOnError { e -> logger.error("Error delete phone", e) }
    }

    private companion object {
        private val logger = LoggerFactory.getLogger(PhoneController::class.java)
    }
}