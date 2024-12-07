package com.xircle.eventservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = [
        "com.xircle.eventservice",
        "com.xircle.common",
    ]
)
@SpringBootApplication
class EventServiceApplication

fun main(args: Array<String>) {
    runApplication<EventServiceApplication>(*args)
}