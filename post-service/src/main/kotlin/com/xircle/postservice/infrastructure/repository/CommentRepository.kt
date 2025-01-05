package com.xircle.postservice.infrastructure.repository

import com.xircle.postservice.domain.model.Comment
import com.xircle.postservice.domain.model.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post = :post AND c.depth = :depth ")
    fun findAllByPostAndDepth(post: Post, depth: Int, pageable: Pageable): List<Comment>

    fun findCommentById(commentId: Long): Comment?

    @Query("SELECT c FROM Comment c WHERE c.parent = :comment ")
    fun findAllByParentComment(comment: Comment, pageable: Pageable): List<Comment>
}