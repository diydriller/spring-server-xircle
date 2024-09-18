package com.xircle.apiserver.application.post.controller

import com.xircle.apiserver.application.post.dto.CreatePostRequest
import com.xircle.apiserver.extension.toPostInfo
import com.xircle.apiserver.security.MemberDetails
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.post.service.PostService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(private val postService: PostService) {
    @PostMapping("/post", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPost(
        request: CreatePostRequest,
        @AuthenticationPrincipal memberDetails: MemberDetails
    ): ResponseEntity<BaseResponse<Any>> {
        val postInfo = request.toPostInfo(memberDetails.getId() as Long)
        postService.createPost(postInfo)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }
}