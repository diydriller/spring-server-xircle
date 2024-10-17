package com.xircle.redis.message

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.util.StringUtil.Companion.CHANNEL_PREFIX
import com.xircle.core.domain.notification.dto.NotificationInfo
import com.xircle.core.message.notification.NotificationMessageListener
import com.xircle.core.message.notification.NotificationMessageSubscriber
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Component

@Component
class NotificationMessageSubscriberImpl(
    private val redisMessageListenerContainer: RedisMessageListenerContainer,
    private val objectMapper: ObjectMapper
) : NotificationMessageSubscriber {
    override fun subscribe(channel: String, listener: NotificationMessageListener) {
        redisMessageListenerContainer.addMessageListener({ message, _ ->
            val emitterKey = String(message.channel).substring(CHANNEL_PREFIX.length)
            val notificationInfo = objectMapper.readValue(
                message.body,
                NotificationInfo::class.java
            )
            listener.onMessage(emitterKey, notificationInfo)
        }, ChannelTopic.of(channel))
    }

    override fun removeSubscribe(channel: String, listener: NotificationMessageListener) {
        redisMessageListenerContainer.removeMessageListener({ message, _ ->
            val emitterKey = String(message.channel).substring(CHANNEL_PREFIX.length)
            val notificationInfo = objectMapper.readValue(
                message.body,
                NotificationInfo::class.java
            )
            listener.onMessage(emitterKey, notificationInfo)
        }, ChannelTopic.of(channel))
    }
}