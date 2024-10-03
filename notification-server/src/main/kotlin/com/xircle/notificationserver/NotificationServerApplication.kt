package com.xircle.notificationserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = [
        "com.xircle.notificationserver",
        "com.xircle.core",
        "com.xircle.common"
    ]
)
@SpringBootApplication
class NotificationServerApplication

fun main(args: Array<String>) {
    runApplication<NotificationServerApplication>(*args)
}