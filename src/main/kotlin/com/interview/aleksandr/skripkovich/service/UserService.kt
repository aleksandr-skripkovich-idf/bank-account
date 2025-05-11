package com.interview.aleksandr.skripkovich.service

import com.interview.aleksandr.skripkovich.model.AuthenticationData
import com.interview.aleksandr.skripkovich.model.SearchUser
import com.interview.aleksandr.skripkovich.model.response.SearchResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface UserService {

    fun authentication(login: String, password: String): Mono<AuthenticationData>
    fun searchUsers(searchUser: SearchUser): Flux<SearchResponse>
}