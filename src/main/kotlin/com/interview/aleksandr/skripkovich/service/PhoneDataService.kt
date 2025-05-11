package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import com.interview.aleksandr.skripkovich.model.db.PhoneEntity
import com.interview.aleksandr.skripkovich.repository.DataRepository
import com.interview.aleksandr.skripkovich.repository.PhoneDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PhoneDataService(
    phoneDataRepository: PhoneDataRepository,
) : AbstractDataService<PhoneEntity>(
    repository = object : DataRepository<PhoneEntity> {
        override fun findByUserIdAndData(userId: Long, data: String) =
            phoneDataRepository.findByUserIdAndPhone(userId, data)

        override fun findByData(data: String) = phoneDataRepository.findByPhone(data)

        override fun save(entity: PhoneEntity) = phoneDataRepository.save(entity)

        override fun deleteById(id: Long) = phoneDataRepository.deleteById(id)

        override fun findPassword(login: String): Mono<PasswordData> = phoneDataRepository.findUserPasswordByPhone(login)
    },
    buildEntity = { userId, data -> PhoneEntity(userId = userId, phone = data) },
    updateEntity = { entity, newData -> entity.copy(phone = newData) },
    getId = { it.id }
)