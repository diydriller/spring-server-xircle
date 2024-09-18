package com.xircle.core.repository.post

import com.xircle.core.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostJpaRepository : JpaRepository<Post, Long> {
}