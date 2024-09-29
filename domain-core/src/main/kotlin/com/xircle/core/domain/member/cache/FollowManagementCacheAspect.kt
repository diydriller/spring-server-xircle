package com.xircle.core.domain.member.cache

import com.xircle.common.util.StringUtil.Companion.getFolloweeKey
import com.xircle.common.util.StringUtil.Companion.getFollowerKey
import com.xircle.core.repository.member.FollowManagementRedisRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component


@Aspect
@Component
class FollowManagementCacheAspect(
    private val followManagementRedisRepository: FollowManagementRedisRepository
) {
    @Around("@annotation(UpdateFollowManagementCache) && args(myId, otherId,..)")
    fun updateFollowCache(joinPoint: ProceedingJoinPoint, myId: Long, otherId: Long): Any? {
        val result = joinPoint.proceed()
        val followerKey = getFollowerKey(myId)
        val followeeKey = getFolloweeKey(otherId)

        followManagementRedisRepository.toggleAndReportSetValue(
            followerKey,
            otherId.toString(),
            followeeKey,
            myId.toString()
        )
        return result
    }
}