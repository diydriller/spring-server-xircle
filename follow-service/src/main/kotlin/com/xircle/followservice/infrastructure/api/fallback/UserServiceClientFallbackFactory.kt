package com.xircle.followservice.infrastructure.api.fallback

import com.xircle.common.dto.MemberInfo
import com.xircle.followservice.infrastructure.api.client.UserServiceClient
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class UserServiceClientFallbackFactory : FallbackFactory<UserServiceClient> {
    override fun create(cause: Throwable): UserServiceClient {
        return object : UserServiceClient {
            override fun getMemberInfoList(memberIdList: List<Long>): List<MemberInfo> {
                return emptyList()
            }
        }
    }
}