package com.xircle.core.repository.post

import com.xircle.core.domain.post.model.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostJpaRepository : JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT p FROM Post p JOIN FETCH p.hashtagList WHERE p.memberId = :memberId")
    fun findPostByMember(@Param("memberId") memberId: Long, pageable: Pageable): List<Post>
}