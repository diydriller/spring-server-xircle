package com.xircle.apiserver.application.post.controller

import com.xircle.apiserver.application.post.dto.CreatePostRequest
import com.xircle.apiserver.application.post.dto.GetProfilePostItem
import com.xircle.apiserver.extension.toPostInfo
import com.xircle.apiserver.security.MemberDetails
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.post.dto.GetPostInfo
import com.xircle.core.domain.post.service.PostService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class PostController(private val postService: PostService) {
    @PostMapping("/post", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPost(
        @Valid request: CreatePostRequest,
        @AuthenticationPrincipal memberDetails: MemberDetails
    ): ResponseEntity<BaseResponse<Any>> {
        val postInfo = request.toPostInfo(memberDetails.username.toLong())
        postService.createPost(postInfo)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }

    @GetMapping("/member/{memberId}/post")
    fun getPostByMember(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable memberId: Long
    ): ResponseEntity<BaseResponse<List<GetPostInfo>>> {
        val getPostInfoList = postService.getPostByMember(page, size, memberId)
        return ResponseEntity.ok().body(BaseResponse(getPostInfoList))
    }

    @GetMapping("/member/{memberId}/profile/post")
    fun getPostByMemberProfile(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable memberId: Long,
        @NotEmpty(message = "hashtag is empty")
        @RequestParam(value = "hashtag") hashtag: String
    ): ResponseEntity<BaseResponse<List<GetProfilePostItem>>> {
        val postList = postService.getProfilePostByMember(page, size, memberId, hashtag)
        val profilePostList = postList.map {
            GetProfilePostItem(it.title, it.postImgSrc)
        }
        return ResponseEntity.ok().body(BaseResponse(profilePostList))
    }

    @GetMapping("/follow/post")
    fun getFollowPost(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @AuthenticationPrincipal memberDetails: MemberDetails
    ): ResponseEntity<BaseResponse<List<GetPostInfo>>> {
        val getFollowPostInfoList = postService.getFollowPost(page, size, memberDetails.username.toLong())
        return ResponseEntity.ok().body(BaseResponse(getFollowPostInfoList))
    }
}