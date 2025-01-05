package com.xircle.followservice.domain.integration.store

import com.xircle.followservice.domain.model.MemberNode

interface FollowStore {
    fun follow(followerId: Long, followeeId: Long)
    fun unfollow(followerId: Long, followeeId: Long)
    fun saveMember(member: MemberNode)
}