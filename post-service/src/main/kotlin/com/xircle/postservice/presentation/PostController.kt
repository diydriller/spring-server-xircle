package com.xircle.postservice.presentation

import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.postservice.application.PostService
import com.xircle.postservice.mapper.CreatePostMapper
import com.xircle.postservice.presentation.dto.CreatePostRequest
import com.xircle.postservice.presentation.dto.GetPostResponse
import com.xircle.postservice.presentation.dto.GetProfilePostResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/post-service")
@RestController
class PostController(private val postService: PostService) {
    @PostMapping("/post", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPost(
        @Valid request: CreatePostRequest,
        @RequestHeader memberId: Long
    ): ResponseEntity<BaseResponse<Any>> {
        val postDto = CreatePostMapper.INSTANCE.covertToDto(request)
        postDto.memberId = memberId
        postService.createPost(postDto)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }

    @GetMapping("/member/{memberId}/post")
    fun getPostByMember(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable memberId: Long
    ): ResponseEntity<BaseResponse<List<GetPostResponse>>> {
        val response = postService.getPostByMember(page, size, memberId).stream()
            .map { post ->
                GetPostResponse(
                    id = post.id!!,
                    title = post.title,
                    content = post.content,
                    postImgSrc = post.postImgSrc,
                    createdAt = post.createdAt!!,
                    hashtagList = post.hashtagList.map { hashtag ->
                        hashtag.name
                    }
                )
            }.toList()
        return ResponseEntity.ok().body(BaseResponse(response))
    }

    @GetMapping("/member/{memberId}/post/preview")
    fun getPostPreview(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable memberId: Long,
        @NotEmpty(message = "hashtag is empty")
        @RequestParam(value = "interest") interest: String
    ): ResponseEntity<BaseResponse<List<GetProfilePostResponse>>> {
        val response = postService.getPostPreview(page, size, memberId, interest).stream()
            .map { post ->
                GetProfilePostResponse(
                    title = post.title,
                    postImgSrc = post.postImgSrc
                )
            }.toList()
        return ResponseEntity.ok().body(BaseResponse(response))
    }

    @GetMapping("/feed")
    fun getFollowPost(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "10") size: Int,
        @RequestHeader(name = "memberId") memberId: Long
    ): ResponseEntity<BaseResponse<List<GetPostResponse>>> {
        val response = postService.getFollowPost(page, size, memberId).stream()
            .map { post ->
                GetPostResponse(
                    id = post.id!!,
                    title = post.title,
                    content = post.content,
                    postImgSrc = post.postImgSrc,
                    createdAt = post.createdAt!!,
                    hashtagList = post.hashtagList.map { hashtag ->
                        hashtag.name
                    }
                )
            }.toList()
        return ResponseEntity.ok().body(BaseResponse(response))
    }

    @GetMapping("/top-commented")
    fun getTopCommentedPost(
        @RequestHeader(name = "memberId") memberId: Long
    ): ResponseEntity<BaseResponse<List<GetPostResponse>>> {
        val response = postService.getTopCommentedPost().stream()
            .map { post ->
                GetPostResponse(
                    id = post.id!!,
                    title = post.title,
                    content = post.content,
                    postImgSrc = post.postImgSrc,
                    createdAt = post.createdAt!!,
                    hashtagList = post.hashtagList.map { hashtag ->
                        hashtag.name
                    }
                )
            }.toList()
        return ResponseEntity.ok().body(BaseResponse(response))
    }
}