package com.xircle.postservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@EnableFeignClients
@ComponentScan(
    basePackages = [
        "com.xircle.postservice",
        "com.xircle.common",
    ]
)
@SpringBootApplication
class PostServiceApplication

fun main(args: Array<String>) {
    runApplication<PostServiceApplication>(*args)
}