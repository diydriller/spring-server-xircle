package com.xircle.core.domain.post.dto

import java.time.LocalDateTime

data class GetPostInfo(
    val id: Long,
    val title: String,
    val content: String,
    val postImgSrc: String,
    val createdAt: LocalDateTime,
    val hashtagList: List<String> = ArrayList()
)

