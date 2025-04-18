package com.xircle.followservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@EnableFeignClients
@ComponentScan(
    basePackages = [
        "com.xircle.followservice",
        "com.xircle.common",
    ]
)
@SpringBootApplication
class FollowServiceApplication

fun main(args: Array<String>) {
    runApplication<FollowServiceApplication>(*args)
}