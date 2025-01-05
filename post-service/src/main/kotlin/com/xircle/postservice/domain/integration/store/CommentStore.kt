package com.xircle.postservice.domain.integration.store

import com.xircle.postservice.domain.model.Comment

interface CommentStore {
    fun saveComment(comment: Comment): Comment
}