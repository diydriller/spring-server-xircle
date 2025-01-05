package com.xircle.followservice.presentation

import com.xircle.common.response.BaseResponse
import com.xircle.followservice.application.FollowService
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FollowController(
    private val followService: FollowService
) {
    @PatchMapping("/follow/{memberId}")
    fun followMember(
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable(name = "memberId") followeeId: Long,
        @RequestHeader(name = "memberId") followerId: Long
    ): ResponseEntity<BaseResponse<Boolean>> {
        val response = followService.followMember(followerId, followeeId)
        return ResponseEntity.ok().body(BaseResponse(response))
    }

    @GetMapping("/follower")
    fun getFollower(
        @RequestHeader(name = "memberId") followerId: Long
    ): List<Long> {
        return followService.getFollower(followerId)
            .map { memberNode ->
                memberNode.id
            }
    }
}