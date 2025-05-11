package com.interview.aleksandr.skripkovich.repository

import com.interview.aleksandr.skripkovich.model.db.EmailEntity
import com.interview.aleksandr.skripkovich.model.db.PasswordData
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface EmailDataRepository : R2dbcRepository<EmailEntity, Long> {

    fun findByEmail(email: String): Mono<EmailEntity>
    fun findByUserIdAndEmail(userId: Long, email: String): Mono<EmailEntity>
    @Query("""
        SELECT u.password, u.id 
        FROM email_data ed 
        LEFT JOIN "user" u ON ed.user_id = u.id 
        WHERE ed.email = :email
    """)
    fun findUserPasswordByEmail(email: String): Mono<PasswordData>
}
