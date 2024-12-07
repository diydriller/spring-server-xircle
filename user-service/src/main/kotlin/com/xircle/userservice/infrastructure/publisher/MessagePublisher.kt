package com.xircle.userservice.infrastructure.publisher

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessagePublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun publish(topic: String, message: Any) {
        kafkaTemplate.send(topic, message)
    }
}