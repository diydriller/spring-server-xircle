package com.xircle.postservice.infrastructure.api

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "follow-service")
interface FollowServiceClient {
    @GetMapping("/follower")
    fun getFollowers(
        @RequestHeader("memberId") memberId: Long
    ): List<Long>
}