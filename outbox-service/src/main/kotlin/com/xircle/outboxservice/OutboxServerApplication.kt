package com.xircle.outboxservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = [
        "com.xircle.outboxservice",
        "com.xircle.common"
    ]
)
@SpringBootApplication
class OutboxServerApplication

fun main(args: Array<String>) {
    runApplication<OutboxServerApplication>(*args)
}