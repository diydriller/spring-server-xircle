package com.xircle.postservice.application

import com.xircle.postservice.application.dto.PostDto
import com.xircle.postservice.domain.integration.reader.FollowReader
import com.xircle.postservice.domain.integration.reader.PostReader
import com.xircle.postservice.domain.integration.store.PostStore
import com.xircle.postservice.domain.model.Hashtag
import com.xircle.postservice.domain.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postStore: PostStore,
    private val postReader: PostReader,
    private val followReader: FollowReader
) {
    @Transactional
    fun createPost(postDto: PostDto): Post {
        val post = Post(
            title = postDto.title,
            content = postDto.content,
            postImgSrc = "",
            memberId = postDto.memberId
        )
        postDto.hashtagList.forEach {
            val hashtag = Hashtag(it)
            post.addHashtag(hashtag)
        }
        return postStore.savePost(post)
    }

    fun getPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        return postReader.findAllPostByMember(page, size, memberId)
    }

    fun getPostPreview(page: Int, size: Int, memberId: Long, interest: String): List<Post> {
        return postReader.findAllPostByMemberInterest(page, size, memberId, interest)
    }

    fun getFollowPost(page: Int, size: Int, memberId: Long): List<Post> {
        val followerIdList = followReader.findAllFollower(memberId)
        return postReader.findAllFollowPostByMember(page, size, followerIdList)
    }

    fun getTopCommentedPost(): List<Post> {
        return postReader.findTop5ByCommentCount()
    }
}