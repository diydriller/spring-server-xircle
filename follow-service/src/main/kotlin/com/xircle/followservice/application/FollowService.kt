package com.xircle.followservice.application

import com.xircle.followservice.domain.integration.reader.FollowReader
import com.xircle.followservice.domain.integration.store.FollowStore
import com.xircle.followservice.domain.model.MemberNode
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followStore: FollowStore,
    private val followReader: FollowReader
) {
    fun toggleFollowMember(followerId: Long, followeeId: Long): Boolean {
        if (followReader.existsFollowRelation(followerId, followeeId)) {
            followStore.unfollow(followerId, followeeId)
            return false
        }
        followStore.follow(followerId, followeeId)
        return true
    }

    fun getFollower(followeeId: Long): List<MemberNode> {
        return followReader.findFollowerList(followeeId)
    }

    fun createUserNode(userId: Long) {
        if (followReader.existsMember(userId)) return

        val member = MemberNode(userId)
        followStore.saveMember(member)
    }
}