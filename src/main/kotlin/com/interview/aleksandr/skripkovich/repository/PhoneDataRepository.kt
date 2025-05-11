package com.interview.aleksandr.skripkovich.repository

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import com.interview.aleksandr.skripkovich.model.db.PhoneEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PhoneDataRepository : R2dbcRepository<PhoneEntity, Long> {
    fun findByPhone(phone: String): Mono<PhoneEntity>
    fun findByUserIdAndPhone(userId: Long, phone: String): Mono<PhoneEntity>
    @Query("""
        SELECT u.password, u.id 
        FROM phone_data pd 
        LEFT JOIN "user" u ON pd.user_id = u.id 
        WHERE pd.phone = :phone
    """)
    fun findUserPasswordByPhone(phone: String): Mono<PasswordData>
}