package com.xircle.postservice.domain.integration.reader

import com.xircle.postservice.domain.model.Comment
import com.xircle.postservice.domain.model.Post

interface CommentReader {
    fun findAllCommentByPost(page: Int, size: Int, post: Post): List<Comment>

    fun findById(commentId: Long): Comment

    fun findAllChildComment(page: Int, size: Int, comment: Comment): List<Comment>
}