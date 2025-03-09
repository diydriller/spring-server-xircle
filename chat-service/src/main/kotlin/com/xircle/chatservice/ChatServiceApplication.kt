package com.xircle.chatservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class ChatServiceApplication

fun main(args: Array<String>) {
    runApplication<ChatServiceApplication>(*args)
}