package com.xircle.followservice.infrastructure.reader

import com.xircle.common.dto.MemberInfo
import com.xircle.followservice.domain.integration.reader.UserReader
import com.xircle.followservice.infrastructure.api.client.UserServiceClient
import org.springframework.stereotype.Component

@Component
class UserReaderImpl(
    private val userServiceClient: UserServiceClient
) : UserReader {
    override fun getMemberInfoList(memberIdList: List<Long>): List<MemberInfo> {
        return userServiceClient.getMemberInfoList(memberIdList)
    }
}