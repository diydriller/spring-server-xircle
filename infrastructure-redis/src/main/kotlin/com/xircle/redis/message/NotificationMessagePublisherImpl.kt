package com.xircle.redis.message

import com.xircle.core.domain.notification.dto.NotificationInfo
import com.xircle.core.message.notification.NotificationMessagePublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class NotificationMessagePublisherImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : NotificationMessagePublisher {
    override fun publish(channel: String, notificationInfo: NotificationInfo) {
        redisTemplate.convertAndSend(channel, notificationInfo)
    }
}