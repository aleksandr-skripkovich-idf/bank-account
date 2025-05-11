package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.db.EmailEntity
import com.interview.aleksandr.skripkovich.model.db.PasswordData
import com.interview.aleksandr.skripkovich.repository.DataRepository
import com.interview.aleksandr.skripkovich.repository.EmailDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EmailDataService(
    emailDataRepository: EmailDataRepository,
) : AbstractDataService<EmailEntity>(
    repository = object : DataRepository<EmailEntity> {
        override fun findByUserIdAndData(userId: Long, data: String) =
            emailDataRepository.findByUserIdAndEmail(userId, data)

        override fun findByData(data: String) = emailDataRepository.findByEmail(data)

        override fun save(entity: EmailEntity) = emailDataRepository.save(entity)

        override fun deleteById(id: Long) = emailDataRepository.deleteById(id)

        override fun findPassword(login: String): Mono<PasswordData> = emailDataRepository.findUserPasswordByEmail(login)
    },
    buildEntity = { userId, data -> EmailEntity(userId = userId, email = data) },
    updateEntity = { entity, newData -> entity.copy(email = newData) },
    getId = { it.id }
)