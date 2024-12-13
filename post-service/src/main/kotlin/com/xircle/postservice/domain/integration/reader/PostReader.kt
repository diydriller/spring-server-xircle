package com.xircle.postservice.domain.integration.reader

import com.xircle.postservice.domain.model.Post

interface PostReader {
    fun findAllPostByMember(page: Int, size: Int, memberId: Long): List<Post>

    fun findAllProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post>

    fun findAllFollower(followeeId: Long): List<Long>

    fun findAllFollowPostByMember(page: Int, size: Int, followerIdList: List<Long>): List<Post>

    fun findAllByIdList(idList: List<Long>): List<Post>
}