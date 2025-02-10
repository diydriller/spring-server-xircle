package com.xircle.postservice.infrastructure.api.client

import com.xircle.postservice.infrastructure.api.fallback.FollowServiceClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "follow-service", fallback = FollowServiceClientFallbackFactory::class)
interface FollowServiceClient {
    @GetMapping("/follow-service/follower")
    fun getFollowerList(
        @RequestHeader("memberId") memberId: Long
    ): List<Long>
}