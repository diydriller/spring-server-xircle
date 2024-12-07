package com.xircle.followservice.application.subsriber

import com.xircle.common.event.UserCreationEventDto
import com.xircle.followservice.domain.integration.reader.FollowReader
import com.xircle.followservice.domain.integration.store.FollowStore
import com.xircle.followservice.domain.model.MemberNode
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserSubscriber(
    private val followStore: FollowStore,
    private val followReader: FollowReader
) {
    @KafkaListener(topics = ["user-created"], groupId = "user-group")
    fun createUserNode(event: UserCreationEventDto) {
        if (followReader.existsMember(event.userId)) return
        val member = MemberNode(event.userId)
        followStore.saveMember(member)
    }
}