package com.xircle.gatewayservice.api.client

import com.xircle.common.dto.MemberInfo
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class UserServiceClientAdapter(
    private val userServiceClient: UserServiceClient
) {
    fun getMemberInfo(memberId: Long): CompletableFuture<MemberInfo> {
        return CompletableFuture.supplyAsync {
            userServiceClient.getMemberInfo(memberId)
        }
    }
}