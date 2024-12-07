package com.xircle.postservice.domain.integration.store

import com.xircle.postservice.domain.model.Post

interface PostStore {
    fun savePost(post: Post): Post
}