package com.xircle.core.store.post

import com.xircle.core.domain.post.model.Post
import com.xircle.core.repository.post.PostJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PostStore(private val postJpaRepository: PostJpaRepository) {
    fun savePost(post: Post) {
        postJpaRepository.save(post)
    }

    fun findPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size)
        return postJpaRepository.findPostByMember(memberId, pageable)
    }
}