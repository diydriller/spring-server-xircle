package com.xircle.core.repository.notification

import com.xircle.common.util.StringUtil.Companion.getChannelName
import com.xircle.core.domain.notification.dto.NotificationInfo
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Repository

@Repository
class RedisMessageService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val redisMessageListenerContainer: RedisMessageListenerContainer,
    private val redisSubscriber: RedisSubscriber
) {
    fun subscribe(channel: String) {
        redisMessageListenerContainer.addMessageListener(redisSubscriber, ChannelTopic.of(channel))
    }

    fun publish(channel: String, notificationInfo: NotificationInfo) {

        redisTemplate.convertAndSend(channel, notificationInfo)
    }

    fun removeSubscribe(channel: String) {
        redisMessageListenerContainer.removeMessageListener(redisSubscriber, ChannelTopic.of(getChannelName(channel)))
    }
}