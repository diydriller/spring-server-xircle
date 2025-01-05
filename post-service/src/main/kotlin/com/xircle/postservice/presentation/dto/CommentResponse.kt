package com.xircle.postservice.presentation.dto

import com.xircle.postservice.domain.model.Comment

data class CommentResponse(
    val id: Long,
    val content: String,
    val depth: Int
) {
    companion object {
        fun fromEntity(comment: Comment): CommentResponse {
            return CommentResponse(
                comment.id!!,
                comment.content,
                comment.depth
            )
        }
    }
}