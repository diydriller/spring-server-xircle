package com.xircle.core.store.post

import com.xircle.core.domain.post.model.Post
import com.xircle.core.repository.post.PostJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PostStore(private val postJpaRepository: PostJpaRepository) {
    fun savePost(post: Post): Post {
        return postJpaRepository.save(post)
    }

    fun findPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postJpaRepository.findPostByMember(memberId, pageable)
    }

    fun findProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postJpaRepository.findProfilePostByMember(memberId, hashtag, pageable)
    }

    fun findFollowPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postJpaRepository.findFollowPostByMember(memberId, pageable)
    }
}