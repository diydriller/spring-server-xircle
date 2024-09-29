package com.xircle.core.repository.post

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val updateFollowerPostScript = """
        local followers = redis.call('SMEMBERS', KEYS[1])
        local currentTime = redis.call('TIME')
        local timestamp = currentTime[1] * 1000 + math.floor(currentTime[2] / 1000)
        for _, followerId in ipairs(followers) do
            redis.call('ZADD', 'member:' .. followerId .. ':posts', timestamp, ARGV[1])
        end
    """.trimIndent()

    fun updateFollowerPost(followeeKey: String, postId: Long) {
        redisTemplate.execute(
            RedisScript.of(updateFollowerPostScript, Void::class.java),
            Collections.singletonList(followeeKey),
            postId
        )
    }

    fun getPagedPostIdList(postKey: String, start: Long, stop: Long): List<Long> {
        return redisTemplate.opsForZSet().reverseRange(postKey, start, stop)
            ?.mapNotNull {
                it.toString().toLong()
            } ?: emptyList()
    }
}