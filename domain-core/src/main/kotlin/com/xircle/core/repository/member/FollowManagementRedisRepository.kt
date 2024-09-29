package com.xircle.core.repository.member

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Component

@Component
class FollowManagementRedisRepository(private val redisTemplate: RedisTemplate<String, Any>) {
    private val toggleAndReportSetValueScript = """
        if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
            redis.call('SREM', KEYS[1], ARGV[1])
            redis.call('SREM', KEYS[2], ARGV[2])
        else
            redis.call('SADD', KEYS[1], ARGV[1])
            redis.call('SADD', KEYS[2], ARGV[2])
        end
    """.trimIndent()

    fun toggleAndReportSetValue(
        followerKey: String,
        followeeId: String,
        followeeKey: String,
        followerId: String
    ) {
        redisTemplate.execute(
            RedisScript.of(toggleAndReportSetValueScript, Void::class.java),
            listOf(followerKey, followeeKey),
            followeeId, followerId
        )
    }
}