package com.xircle.core.domain.member.service

import com.xircle.core.domain.member.cache.UpdateFollowManagementCache
import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.domain.notification.dto.NotificationEventDto
import com.xircle.core.domain.notification.enum.NotificationType
import com.xircle.core.domain.notification.event.NotificationEventPublisher
import com.xircle.core.store.member.MemberStore
import org.springframework.stereotype.Service

@Service
class FollowManagementService(
    private val memberStore: MemberStore,
    private val notificationEventPublisher: NotificationEventPublisher
) {
    @UpdateFollowManagementCache
    fun followMember(myId: Long, otherId: Long): Boolean {
        val me = memberStore.findMemberById(myId)
        val other = memberStore.findMemberById(otherId)
        var isFollowing = false
        memberStore.findFollow(me, other)?.let {
            it.isDeleted = !it.isDeleted
            memberStore.saveFollowerFollowee(it)
            isFollowing = !it.isDeleted
        } ?: run {
            val followerFollowee = FollowerFollowee(me, other)
            memberStore.saveFollowerFollowee(followerFollowee)
            isFollowing = true
        }
        if(isFollowing) {
            notificationEventPublisher.publishEvent(NotificationEventDto(otherId, NotificationType.FOLLOW))
        }
        return isFollowing
    }
}