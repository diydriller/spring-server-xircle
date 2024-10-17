package com.xircle.core.store.post

import com.xircle.core.domain.post.model.Post
import org.springframework.stereotype.Repository

@Repository
interface PostStore {
    fun savePost(post: Post): Post
    fun findPostByMember(page: Int, size: Int, memberId: Long): List<Post>
    fun findProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post>
    fun findFollowPostByMember(page: Int, size: Int, memberId: Long): List<Post>
    fun findAllByIdList(idList: List<Long>): List<Post>
}