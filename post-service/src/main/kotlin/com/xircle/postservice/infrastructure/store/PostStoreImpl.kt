package com.xircle.postservice.infrastructure.store

import com.xircle.postservice.domain.integration.store.PostStore
import com.xircle.postservice.domain.model.Post
import com.xircle.postservice.infrastructure.repository.PostRepository
import org.springframework.stereotype.Component

@Component
class PostStoreImpl(private val postRepository: PostRepository) : PostStore {
    override fun savePost(post: Post): Post {
        return postRepository.save(post)
    }
}