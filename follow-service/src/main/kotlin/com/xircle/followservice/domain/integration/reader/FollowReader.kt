package com.xircle.followservice.domain.integration.reader

import com.xircle.followservice.domain.model.MemberNode

interface FollowReader {
    fun findFollowers(followeeId: Long, page: Int, size: Int): List<MemberNode>
    fun findFollowees(followerId: Long, page: Int, size: Int): List<MemberNode>
    fun existsFollowRelation(followerId: Long, followeeId: Long): Boolean
    fun existsMember(memberId: Long): Boolean
}