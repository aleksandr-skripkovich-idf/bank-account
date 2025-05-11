package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class CacheService(
    private val redisTemplate: ReactiveRedisTemplate<String, PasswordData>,
    @Value("\${spring.redis.ttl}")
    private val ttl: String
) {

    fun saveToCache(key: String, value: PasswordData): Mono<Boolean> {
        return redisTemplate.opsForValue().set(key, value, Duration.parse(ttl))
    }

    fun getFromCache(key: String): Mono<PasswordData> {
        return redisTemplate.opsForValue().get(key)
    }

    fun deleteFromCache(key: String): Mono<Boolean> {
        return redisTemplate.delete(key)
            .hasElement()
    }
}