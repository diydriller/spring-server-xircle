package com.xircle.apiserver.application.member.controller

import com.xircle.apiserver.security.MemberDetails
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.service.FollowManagementService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowManagementController(
    private val followManagementService: FollowManagementService
) {
    @PatchMapping("/follow/{memberId}")
    fun followMember(
        @AuthenticationPrincipal memberDetails: MemberDetails,
        @PathVariable("memberId") memberId: Long
    ): ResponseEntity<BaseResponse<Unit>> {
        val isFollowing = followManagementService.followMember(memberDetails.getId() as Long, memberId)
        if (isFollowing) {
            return ResponseEntity.ok()
                .body(BaseResponse(BaseResponseStatus.FOLLOW_SUCCESS))
        }
        return ResponseEntity.ok()
            .body(BaseResponse(BaseResponseStatus.UNFOLLOW_SUCCESS))
    }
}