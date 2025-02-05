package com.xircle.userservice.infrastructure.publisher

import com.xircle.common.util.JsonUtil.toJson
import com.xircle.userservice.infrastructure.model.Outbox
import com.xircle.userservice.infrastructure.repository.outbox.OutboxRepository
import org.springframework.stereotype.Component


@Component
class MessagePublisher(
    private val outboxRepository: OutboxRepository,
) {
    fun publish(topic: String, message: Any) {
        val payload = toJson(message)
        val outbox = Outbox(topic, payload)
        outboxRepository.save(outbox)
    }
}