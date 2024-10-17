package com.xircle.core.store.post

import org.springframework.stereotype.Repository

@Repository
interface PostCacheStore {
    fun updateFollowerPost(followeeKey: String, postId: Long)
    fun getPagedPostIdList(postKey: String, start: Long, stop: Long): List<Long>
}