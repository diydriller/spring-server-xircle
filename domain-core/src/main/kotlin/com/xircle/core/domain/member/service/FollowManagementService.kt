package com.xircle.core.domain.member.service

import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.store.member.MemberStore
import org.springframework.stereotype.Service

@Service
class FollowManagementService(
    private val memberStore: MemberStore,
) {
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
        return isFollowing
    }
}