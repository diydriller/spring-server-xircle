package com.xircle.followservice.infrastructure.subscriber

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.event.UserCreationEventDto
import com.xircle.common.util.StringUtil.Companion.CREATE_USER_TOPIC
import com.xircle.followservice.application.FollowService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class UserSubscriber(
    private val followService: FollowService,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserSubscriber::class.java)
    }

    @KafkaListener(topics = [CREATE_USER_TOPIC], groupId = "user-group")
    fun createUserNode(message: String, acknowledgment: Acknowledgment) {
        try {
            val eventDto = objectMapper.readValue(message, UserCreationEventDto::class.java)
            followService.createUserNode(eventDto.userId)
            acknowledgment.acknowledge()
        } catch (ex: Exception) {
            log.error(ex.message)
        }
    }
}