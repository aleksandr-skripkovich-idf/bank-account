package com.interview.aleksandr.skripkovich

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class BankAccountApp

fun main(args: Array<String>) {
    runApplication<com.interview.aleksandr.skripkovich.BankAccountApp>(*args)
}