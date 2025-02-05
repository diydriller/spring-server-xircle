package com.xircle.outboxservice.infrastructure.job

import com.xircle.common.util.StringUtil.Companion.CREATE_USER_TOPIC
import com.xircle.outboxservice.infrastructure.model.Outbox
import com.xircle.outboxservice.infrastructure.repository.OutboxRepository
import jakarta.transaction.Transactional
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OutboxProcessorJob(
    private val outboxRepository: OutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : Job {

    override fun execute(context: JobExecutionContext) {
        val outboxMessages = outboxRepository.findAllByStatus(Outbox.OutboxStatus.PENDING)

        outboxMessages.forEach { outbox ->
            kafkaTemplate.send(CREATE_USER_TOPIC, outbox.payload).whenComplete { _, exception ->
                if (exception == null) {
                    outboxRepository.delete(outbox)
                }
                else{
                    outbox.status = Outbox.OutboxStatus.FAILED
                    outboxRepository.save(outbox)
                }
            }
        }
    }
}