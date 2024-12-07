package com.xircle.postservice.infrastructure.reader

import com.xircle.postservice.domain.integration.reader.PostReader
import com.xircle.postservice.domain.model.Post
import com.xircle.postservice.infrastructure.api.FollowServiceClient
import com.xircle.postservice.infrastructure.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PostReaderImpl(
    private val postRepository: PostRepository,
    private val followServiceClient: FollowServiceClient
) : PostReader {
    override fun findAllPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postRepository.findPostByMember(memberId, pageable)
    }

    override fun findAllProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postRepository.findProfilePostByMember(memberId, hashtag, pageable)
    }

    override fun findAllFollower(page: Int, size: Int, followeeId: Long): List<Long> {
        return followServiceClient.getFollowers(page, size, followeeId)
    }

    override fun findAllFollowPostByMember(page: Int, size: Int, followerIdList: List<Long>): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postRepository.findFollowPostByMember(followerIdList, pageable)
    }

    override fun findAllByIdList(idList: List<Long>): List<Post> {
        return postRepository.findAllByIsDeletedAndIdIn(false, idList)
    }
}