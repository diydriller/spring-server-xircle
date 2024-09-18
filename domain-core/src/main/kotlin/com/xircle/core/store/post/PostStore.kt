package com.xircle.core.store.post

import com.xircle.core.domain.post.model.Post
import com.xircle.core.repository.post.PostJpaRepository
import org.springframework.stereotype.Component

@Component
class PostStore(private val postJpaRepository: PostJpaRepository) {
    fun savePost(post: Post) {
        postJpaRepository.save(post)
    }
}