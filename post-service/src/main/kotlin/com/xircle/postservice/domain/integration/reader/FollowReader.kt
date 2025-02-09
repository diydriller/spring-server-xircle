package com.xircle.postservice.domain.integration.reader

interface FollowReader {
    fun findAllFollower(followeeId: Long): List<Long>
}