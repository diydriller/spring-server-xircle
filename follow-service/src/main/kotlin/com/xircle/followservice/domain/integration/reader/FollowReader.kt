package com.xircle.followservice.domain.integration.reader

import com.xircle.followservice.domain.model.MemberNode

interface FollowReader {
    fun findFollowers(followeeId: Long): List<MemberNode>
    fun findFollowees(followerId: Long): List<MemberNode>
    fun existsFollowRelation(followerId: Long, followeeId: Long): Boolean
    fun existsMember(memberId: Long): Boolean
}