package com.xircle.core.store.member

import org.springframework.stereotype.Repository

@Repository
interface MemberCacheStore {
    fun toggleAndReportSetValue(
        followerKey: String,
        followeeId: String,
        followeeKey: String,
        followerId: String
    )
}