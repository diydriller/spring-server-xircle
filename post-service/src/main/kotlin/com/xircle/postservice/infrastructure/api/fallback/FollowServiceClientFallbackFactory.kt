package com.xircle.postservice.infrastructure.api.fallback

import com.xircle.postservice.infrastructure.api.client.FollowServiceClient
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class FollowServiceClientFallbackFactory : FallbackFactory<FollowServiceClient> {
    override fun create(cause: Throwable): FollowServiceClient {
        return object : FollowServiceClient {
            override fun getFollowers(memberId: Long): List<Long> {
                return emptyList()
            }
        }
    }
}