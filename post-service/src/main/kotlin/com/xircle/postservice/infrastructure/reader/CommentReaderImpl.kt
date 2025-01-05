package com.xircle.postservice.infrastructure.reader

import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.postservice.domain.integration.reader.CommentReader
import com.xircle.postservice.domain.model.Comment
import com.xircle.postservice.domain.model.Post
import com.xircle.postservice.infrastructure.repository.CommentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class CommentReaderImpl(
    private val commentRepository: CommentRepository
) : CommentReader {
    override fun findAllCommentByPost(page: Int, size: Int, post: Post): List<Comment> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("createdAt").descending())
        return commentRepository.findAllByPostAndDepth(post, 0, pageable)
    }

    override fun findById(commentId: Long): Comment {
        return commentRepository.findCommentById(commentId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_COMMENT)
    }

    override fun findAllChildComment(page: Int, size: Int, comment: Comment): List<Comment> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("createdAt").descending())
        return commentRepository.findAllByParentComment(comment, pageable)
    }
}