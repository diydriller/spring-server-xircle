package com.xircle.followservice.application

import com.xircle.common.event.UserCreationEventDto
import com.xircle.followservice.domain.integration.reader.FollowReader
import com.xircle.followservice.domain.integration.store.FollowStore
import com.xircle.followservice.domain.model.MemberNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class UserSubscriber(
    private val followStore: FollowStore,
    private val followReader: FollowReader
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserSubscriber::class.java)
    }

    @KafkaListener(topics = ["user-created"], groupId = "user-group")
    fun createUserNode(event: UserCreationEventDto, acknowledgment: Acknowledgment) {
        try {
            if (followReader.existsMember(event.userId)) return
            val member = MemberNode(event.userId)
            followStore.saveMember(member)
            acknowledgment.acknowledge()
        } catch (ex: Exception) {
            log.error(ex.message)
        }
    }
}