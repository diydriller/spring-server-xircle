package com.xircle.postservice.application.service

import com.xircle.postservice.application.dto.PostDto
import com.xircle.postservice.domain.integration.reader.PostReader
import com.xircle.postservice.domain.integration.store.PostStore
import com.xircle.postservice.domain.model.Hashtag
import com.xircle.postservice.domain.model.Post
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postStore: PostStore,
    private val postReader: PostReader
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

    @Transactional
    fun getPostByMember(page: Int, size: Int, memberId: Long): List<Post> {
        return postReader.findAllPostByMember(page, size, memberId)
    }

    @Transactional
    fun getProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post> {
        return postReader.findAllProfilePostByMember(page, size, memberId, hashtag)
    }

    fun getFollowPost(page: Int, size: Int, memberId: Long): List<Post> {
        val followerIdList = postReader.findAllFollower(page, size, memberId)
        return postReader.findAllFollowPostByMember(page, size, followerIdList)
    }
}