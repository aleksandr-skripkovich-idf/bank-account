package com.interview.aleksandr.skripkovich.service

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

private const val AUTH_USER = "AuthUser"

@Component
class CookiesUtil {

    fun setJwt(jwt: String, response: HttpServletResponse) {
        val cookie = Cookie(AUTH_USER, jwt)
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.maxAge = 3600

        response.addCookie(cookie)
    }

    fun extractJwt(request: HttpServletRequest): String? {
        return request.cookies
            ?.firstOrNull { cookie -> cookie.name == AUTH_USER }
            ?.value
    }
}