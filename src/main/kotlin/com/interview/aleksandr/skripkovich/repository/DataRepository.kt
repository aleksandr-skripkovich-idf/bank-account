package com.interview.aleksandr.skripkovich.repository

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import reactor.core.publisher.Mono

interface DataRepository<T> {
    fun findByUserIdAndData(userId: Long, data: String): Mono<T>
    fun findByData(data: String): Mono<T>
    fun save(entity: T): Mono<T>
    fun deleteById(id: Long): Mono<Void>
    fun findPassword(login: String): Mono<PasswordData>
}