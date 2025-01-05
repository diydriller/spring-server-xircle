package com.xircle.postservice.infrastructure.repository

import com.xircle.postservice.domain.model.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.memberId = :memberId")
    fun findPostByMember(memberId: Long, pageable: Pageable): List<Post>

    @Query("SELECT DISTINCT p FROM Post p JOIN p.hashtagList h WHERE p.memberId = :memberId AND h.name LIKE %:hashtag%")
    fun findAllPostByMemberInterest(memberId: Long, hashtag: String, pageable: Pageable): List<Post>

    @Query("SELECT p FROM Post p WHERE p.memberId IN :followerIdList")
    fun findFollowPostByMember(followerIdList: List<Long>, pageable: Pageable): List<Post>

    @Query("SELECT p FROM Post p WHERE p.id IN :postIdList AND p.isDeleted = :isDeleted")
    fun findAllByIsDeletedAndIdIn(isDeleted: Boolean, postIdList: List<Long>): List<Post>

    fun findPostById(postId: Long): Post?
}