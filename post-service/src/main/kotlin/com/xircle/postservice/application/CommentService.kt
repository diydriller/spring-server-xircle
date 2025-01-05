package com.xircle.postservice.application

import com.xircle.postservice.application.dto.CommentDto
import com.xircle.postservice.domain.integration.reader.CommentReader
import com.xircle.postservice.domain.integration.reader.PostReader
import com.xircle.postservice.domain.integration.store.CommentStore
import com.xircle.postservice.domain.model.Comment
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val postReader: PostReader,
    private val commentStore: CommentStore,
    private val commentReader: CommentReader
) {
    @Transactional
    fun createComment(postId: Long, memberId: Long, commentDto: CommentDto): Comment {
        val post = postReader.findById(postId)
        val comment = Comment(
            post = post,
            content = commentDto.content,
            memberId = memberId
        )
        val savedComment = commentStore.saveComment(comment)
        return savedComment
    }

    fun getComment(page: Int, size: Int, postId: Long): List<Comment> {
        val post = postReader.findById(postId)
        return commentReader.findAllCommentByPost(page, size, post)
    }

    @Transactional
    fun createRecomment(postId: Long, commentId: Long, memberId: Long, commentDto: CommentDto): Comment {
        val post = postReader.findById(postId)
        val comment = commentReader.findById(commentId)
        val savedComment = comment.createRecomment(
            Comment(
                post = post,
                commentDto.content,
                depth = comment.depth + 1,
                memberId = memberId
            )
        )
        return savedComment
    }

    fun getRecomment(page: Int, size: Int, postId: Long, commentId: Long): List<Comment> {
        postReader.findById(postId)
        val comment = commentReader.findById(commentId)
        return commentReader.findAllChildComment(page, size, comment)
    }
}