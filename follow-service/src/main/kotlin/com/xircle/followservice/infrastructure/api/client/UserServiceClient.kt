package com.xircle.followservice.infrastructure.api.client

import com.xircle.common.dto.MemberInfo
import com.xircle.followservice.infrastructure.api.fallback.UserServiceClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "user-service", fallback = UserServiceClientFallbackFactory::class)
interface UserServiceClient {
    @GetMapping("/user-service/member/info")
    fun getMemberInfoList(@RequestParam memberIdList: List<Long>): List<MemberInfo>
}