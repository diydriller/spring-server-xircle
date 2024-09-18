package com.xircle.core.domain.post.service

import com.xircle.core.domain.post.dto.PostInfo
import com.xircle.core.domain.post.model.Hashtag
import com.xircle.core.domain.post.model.Post
import com.xircle.core.store.post.PostStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostService(private val postStore: PostStore) {
    @Transactional
    fun createPost(postInfo: PostInfo) {
        val post = Post(
            title = postInfo.title,
            content = postInfo.content,
            postImgSrc = "",
            memberId = postInfo.memberId
        )
        postInfo.hashtagList.forEach{
            val hashtag = Hashtag(it)
            post.addHashtag(hashtag)
        }
        postStore.savePost(post)
    }
}