package com.xircle.postservice.infrastructure.store

import com.xircle.postservice.domain.integration.store.CommentStore
import com.xircle.postservice.domain.model.Comment
import com.xircle.postservice.infrastructure.repository.CommentRepository
import org.springframework.stereotype.Component

@Component
class CommentStoreImpl(
    private val commentRepository: CommentRepository
) : CommentStore {
    override fun saveComment(comment: Comment): Comment {
        return commentRepository.save(comment)
    }
}