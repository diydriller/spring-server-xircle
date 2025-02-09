package com.xircle.postservice.infrastructure.reader

import com.xircle.postservice.domain.integration.reader.FollowReader
import com.xircle.postservice.infrastructure.api.client.FollowServiceClient
import org.springframework.stereotype.Component

@Component
class FollowReaderImpl(
    private val followServiceClient: FollowServiceClient,
) : FollowReader {
    override fun findAllFollower(followeeId: Long): List<Long> {
        return followServiceClient.getFollowerList(followeeId)
    }
}