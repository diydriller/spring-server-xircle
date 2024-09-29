package com.xircle.core.domain.post.service

import com.xircle.core.domain.post.cache.GetPostCache
import com.xircle.core.domain.post.cache.UpdatePostCache
import com.xircle.core.domain.post.dto.GetPostInfo
import com.xircle.core.domain.post.dto.PostInfo
import com.xircle.core.domain.post.model.Hashtag
import com.xircle.core.domain.post.model.Post
import com.xircle.core.store.post.PostStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostService(
    private val postStore: PostStore
) {
    @UpdatePostCache
    @Transactional
    fun createPost(postInfo: PostInfo): Post {
        val post = Post(
            title = postInfo.title,
            content = postInfo.content,
            postImgSrc = "",
            memberId = postInfo.memberId
        )
        postInfo.hashtagList.forEach {
            val hashtag = Hashtag(it)
            post.addHashtag(hashtag)
        }
        return postStore.savePost(post)
    }

    @Transactional
    fun getPostByMember(page: Int, size: Int, memberId: Long): List<GetPostInfo> {
        return postStore.findPostByMember(page, size, memberId).map {
            GetPostInfo(
                it.id as Long,
                it.title,
                it.content,
                it.postImgSrc,
                it.createdAt as LocalDateTime,
                it.hashtagList.map { hashtag -> hashtag.name }
            )
        }
    }

    @Transactional
    fun getProfilePostByMember(page: Int, size: Int, memberId: Long, hashtag: String): List<Post> {
        return postStore.findProfilePostByMember(page, size, memberId, hashtag)
    }

    @GetPostCache
    @Transactional
    fun getFollowPost(page: Int, size: Int, memberId: Long): List<GetPostInfo> {
        return postStore.findFollowPostByMember(page, size, memberId).map {
            GetPostInfo(
                it.id as Long,
                it.title,
                it.content,
                it.postImgSrc,
                it.createdAt as LocalDateTime,
                it.hashtagList.map { hashtag -> hashtag.name }
            )
        }
    }
}