package com.xircle.postservice.infrastructure.reader

import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.postservice.domain.integration.reader.PostReader
import com.xircle.postservice.domain.model.Post
import com.xircle.postservice.infrastructure.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PostReaderImpl(
    private val postRepository: PostRepository,
) : PostReader {
    override fun findAllPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postRepository.findPostByMember(memberId, pageable)
    }

    override fun findAllPostByMemberInterest(page: Int, size: Int, memberId: Long, interest: String): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postRepository.findAllPostByMemberInterest(memberId, interest, pageable)
    }

    override fun findAllFollowPostByMember(page: Int, size: Int, followerIdList: List<Long>): List<Post> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        return postRepository.findFollowPostByMember(followerIdList, pageable)
    }

    override fun findAllByIdList(idList: List<Long>): List<Post> {
        return postRepository.findAllByIsDeletedAndIdIn(false, idList)
    }

    override fun findById(postId: Long): Post {
        return postRepository.findPostById(postId) ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_POST)
    }

    override fun findTop5ByCommentCount(): List<Post> {
        val pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "createdAt")
        return postRepository.findTop5ByCommentCount(pageable)
    }
}