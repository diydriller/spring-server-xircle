package com.xircle.jpa.store.post

import com.xircle.core.domain.post.model.Post
import com.xircle.core.store.post.PostStore
import com.xircle.jpa.repository.post.PostJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PostStoreImpl(private val postJpaRepository: PostJpaRepository) : PostStore {
    override fun savePost(post: Post): Post {
        return postJpaRepository.save(post)
    }

    override fun findPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postJpaRepository.findPostByMember(memberId, pageable)
    }

    override fun findProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postJpaRepository.findProfilePostByMember(memberId, hashtag, pageable)
    }

    override fun findFollowPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postJpaRepository.findFollowPostByMember(memberId, pageable)
    }

    override fun findAllByIdList(idList: List<Long>): List<Post> {
        return postJpaRepository.findAllByIsDeletedAndIdIn(false, idList)
    }
}