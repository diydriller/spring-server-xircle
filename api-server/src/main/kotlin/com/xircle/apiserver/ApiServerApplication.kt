package com.xircle.apiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = [
        "com.xircle.apiserver",
        "com.xircle.core",
        "com.xircle.common",
        "com.xircle.jpa",
        "com.xircle.redis"
    ]
)
@SpringBootApplication
class ApiServerApplication

fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}