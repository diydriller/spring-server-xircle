package com.xircle.chatservice.domain.integration.publisher

interface MessagePublisher {
    fun publish(topic: String, message: Any)
}