package com.xircle.userservice.infrastructure.publisher

import com.xircle.common.util.JsonUtil.fromJson
import com.xircle.common.util.JsonUtil.toJson
import com.xircle.userservice.infrastructure.outbox.Outbox
import com.xircle.userservice.infrastructure.outbox.OutboxRepository
import com.xircle.userservice.infrastructure.outbox.OutboxStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class MessagePublisher(
    private val outboxRepository: OutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun publish(topic: String, message: Any) {
        val payload = toJson(message)
        val outbox = Outbox(topic, payload, message.javaClass.name)
        outboxRepository.save(outbox)
    }

    @Transactional
    @Scheduled(fixedRate = 5000)
    fun processOutbox() {
        outboxRepository.findAllByStatus(OutboxStatus.PENDING)
            .forEach { outbox ->
                val message = fromJson(outbox.payload, outbox.className)
                val topic = outbox.topic
                kafkaTemplate.executeInTransaction {
                    it.send(topic, message)
                    outboxRepository.delete(outbox)
                }
            }
    }
}