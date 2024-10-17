package com.xircle.jpa.repository.post

import com.xircle.core.domain.post.model.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostJpaRepository : JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.memberId = :memberId")
    fun findPostByMember(@Param("memberId") memberId: Long, pageable: Pageable): List<Post>

    @Query("SELECT DISTINCT p FROM Post p JOIN p.hashtagList h WHERE p.memberId = :memberId AND h.name LIKE %:hashtag%")
    fun findProfilePostByMember(
        @Param("memberId") memberId: Long,
        @Param("hashtag") hashtag: String,
        pageable: Pageable
    ): List<Post>

    @Query(
        "SELECT p FROM Post p JOIN FollowerFollowee f ON p.memberId = f.followee.id " +
                "WHERE f.follower.id = :memberId"
    )
    fun findFollowPostByMember(
        @Param("memberId") memberId: Long,
        pageable: Pageable
    ): List<Post>

    @Query("SELECT p FROM Post p WHERE p.id IN :postIdList AND p.isDeleted = :isDeleted")
    fun findAllByIsDeletedAndIdIn(
        @Param("isDeleted") isDeleted: Boolean,
        @Param("postIdList") postIdList: List<Long>
    ): List<Post>
}