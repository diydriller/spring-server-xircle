package com.xircle.gatewayservice.api.client

import com.xircle.common.dto.MemberInfo
import com.xircle.gatewayservice.api.fallback.UserServiceClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", fallback = UserServiceClientFallbackFactory::class)
interface UserServiceClient {
    @GetMapping("/user-service/member/{memberId}")
    fun getMemberInfo(@PathVariable("memberId") memberId: Long): MemberInfo?
}