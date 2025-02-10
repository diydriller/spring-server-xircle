package com.xircle.gatewayservice.api.fallback

import com.xircle.common.dto.MemberInfo
import com.xircle.gatewayservice.api.client.UserServiceClient
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class UserServiceClientFallbackFactory : FallbackFactory<UserServiceClient> {
    override fun create(cause: Throwable): UserServiceClient {
        return object : UserServiceClient {
            override fun getMemberInfo(memberId: Long): MemberInfo? {
                return null
            }
        }
    }
}