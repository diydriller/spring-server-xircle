package com.xircle.followservice.domain.integration.reader

import com.xircle.followservice.domain.model.MemberNode

interface FollowReader {
    fun findFollowerList(followeeId: Long): List<MemberNode>
    fun findFolloweeList(followerId: Long): List<MemberNode>
    fun existsFollowRelation(followerId: Long, followeeId: Long): Boolean
    fun existsMember(memberId: Long): Boolean
    fun recommendFollowerOfFollower(memberId: Long): List<MemberNode>
}