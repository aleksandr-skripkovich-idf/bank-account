package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.db.PasswordData
import com.interview.aleksandr.skripkovich.model.response.DefaultResponse
import com.interview.aleksandr.skripkovich.model.response.Response
import com.interview.aleksandr.skripkovich.repository.DataRepository
import reactor.core.publisher.Mono

abstract class AbstractDataService<T>(
    private val repository: DataRepository<T>,
    private val buildEntity: (Long, String) -> T,
    private val updateEntity: (T, String) -> T,
    private val getId: (T) -> Long?
) : DataService {

    override fun create(userId: Long, data: String): Mono<DefaultResponse> {
        return existData(data)
            .filter { exist -> !exist }
            .flatMap { repository.save(buildEntity(userId, data)) }
            .map { DefaultResponse(Response.SUCCESS) }
            .defaultIfEmpty(DefaultResponse(Response.NOT_SUCCESS))
            .onErrorReturn(DefaultResponse(Response.NOT_SUCCESS))
    }

    override fun update(userId: Long, newData: String, oldData: String): Mono<DefaultResponse> {
        return existData(newData)
            .filter { exist -> !exist }
            .flatMap { updatePhone(newData, oldData) }
            .defaultIfEmpty(Response.NOT_SUCCESS)
            .onErrorReturn(Response.NOT_SUCCESS)
            .map { result -> DefaultResponse(response = result) }
    }

    override fun delete(userId: Long, data: String): Mono<DefaultResponse> {
        return repository.findByUserIdAndData(userId, data)
            .delayUntil { entity -> repository.deleteById(getId(entity)!!) }
            .map { DefaultResponse(Response.SUCCESS) }
            .defaultIfEmpty(DefaultResponse(Response.NOT_SUCCESS))
    }

    override fun getAuthentication(login: String): Mono<PasswordData> {
        return repository.findPassword(login)
    }

    private fun existData(data: String): Mono<Boolean> {
        return repository.findByData(data)
            .hasElement()
    }

    private fun updatePhone(newData: String, oldData: String): Mono<Response> {
        return repository.findByData(oldData)
            .flatMap {
                repository.findByData(oldData)
                    .flatMap { entity -> repository.save(updateEntity(entity, newData)) }
            }
            .map { Response.SUCCESS }
            .defaultIfEmpty(Response.NOT_SUCCESS)

    }
}