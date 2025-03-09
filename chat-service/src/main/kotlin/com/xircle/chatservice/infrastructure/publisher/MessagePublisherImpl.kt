package com.xircle.chatservice.infrastructure.publisher

import com.xircle.chatservice.domain.integration.publisher.MessagePublisher
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessagePublisherImpl(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : MessagePublisher {
    override fun publish(topic: String, message: Any) {
        kafkaTemplate.send(topic, message)
    }
}