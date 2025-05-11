package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import com.interview.aleksandr.skripkovich.model.response.DefaultResponse
import reactor.core.publisher.Mono

interface DataService {
    fun update(userId: Long, newData: String, oldData: String): Mono<DefaultResponse>
    fun create(userId: Long, data: String): Mono<DefaultResponse>
    fun delete(userId: Long, data: String): Mono<DefaultResponse>
    fun getAuthentication(login: String): Mono<PasswordData>
}