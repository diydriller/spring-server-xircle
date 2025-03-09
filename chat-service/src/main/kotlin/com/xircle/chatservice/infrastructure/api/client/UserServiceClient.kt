package com.xircle.chatservice.infrastructure.api.client

import com.xircle.chatservice.infrastructure.api.fallback.UserServiceClientFallbackFactory
import com.xircle.common.dto.MemberInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", fallback = UserServiceClientFallbackFactory::class)
interface UserServiceClient {
    @GetMapping("/user-service/member/{memberId}")
    fun getMemberInfo(@PathVariable("memberId") memberId: Long): MemberInfo?
}