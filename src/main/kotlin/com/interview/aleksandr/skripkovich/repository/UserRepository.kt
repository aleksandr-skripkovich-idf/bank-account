package com.interview.aleksandr.skripkovich.repository

import com.interview.aleksandr.skripkovich.model.db.SearchUserEntity
import com.interview.aleksandr.skripkovich.model.db.UserEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDate

@Repository
interface UserRepository : R2dbcRepository<UserEntity, Long> {

    @Query(
        """
    SELECT
        u.name,
        u.date_of_birth as dateOfBirth,
        ed.email,
        pd.phone
    FROM "user" u
    LEFT JOIN email_data ed on u.id = ed.user_id
    LEFT JOIN phone_data pd on u.id = pd.user_id
    WHERE (:dob IS NULL OR u.date_of_birth > :dob)
      AND (:phone IS NULL OR pd.phone = :phone)
      AND (:name IS NULL OR u.name ILIKE CONCAT(:name, '%'))
      AND (:email IS NULL OR ed.email = :email)
    ORDER BY u.id
    LIMIT :limit OFFSET :offset
"""
    )
    fun searchUsers(
        dob: LocalDate?,
        phone: String?,
        name: String?,
        email: String?,
        limit: Int,
        offset: Int
    ): Flux<SearchUserEntity>
}