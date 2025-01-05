package com.xircle.postservice.presentation.dto

import java.time.LocalDateTime

data class GetPostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val postImgSrc: String,
    val createdAt: LocalDateTime,
    val hashtagList: List<String> = ArrayList()
)