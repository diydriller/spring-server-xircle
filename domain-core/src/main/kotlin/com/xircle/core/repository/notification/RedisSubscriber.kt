package com.xircle.core.repository.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.util.StringUtil.Companion.CHANNEL_PREFIX
import com.xircle.core.domain.notification.dto.NotificationInfo
import com.xircle.core.domain.notification.service.SseEmitterService
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RedisSubscriber(
    private val objectMapper: ObjectMapper,
    private val sseEmitterService: SseEmitterService
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val channel = String(message.channel).substring(CHANNEL_PREFIX.length)

            val notificationInfo = objectMapper.readValue(
                message.body,
                NotificationInfo::class.java
            )
            sseEmitterService.sendNotificationToClient(channel, notificationInfo)
        } catch (_: Exception) {
        }
    }
}