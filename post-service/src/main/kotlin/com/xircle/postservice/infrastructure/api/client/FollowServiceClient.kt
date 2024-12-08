package com.xircle.postservice.infrastructure.api.client

import com.xircle.postservice.infrastructure.api.fallback.FollowServiceClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "follow-service", fallback = FollowServiceClientFallbackFactory::class)
interface FollowServiceClient {
    @GetMapping("/follower")
    fun getFollowers(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestHeader("memberId") memberId: Long
    ): List<Long>
}