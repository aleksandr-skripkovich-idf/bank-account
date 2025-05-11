package com.interview.aleksandr.skripkovich.config

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
open class RedisConfig {

    @Bean
    open fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, PasswordData> {
        val serializationContext = RedisSerializationContext
            .newSerializationContext<String, PasswordData>()
            .key(StringRedisSerializer())
            .value(Jackson2JsonRedisSerializer(PasswordData::class.java))
            .hashKey(StringRedisSerializer())
            .hashValue(RedisSerializer.json())
            .build()

        return ReactiveRedisTemplate(factory, serializationContext)
    }
}