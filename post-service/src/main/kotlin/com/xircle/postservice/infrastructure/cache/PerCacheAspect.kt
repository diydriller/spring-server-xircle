package com.xircle.postservice.infrastructure.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.postservice.domain.model.Post
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import kotlin.math.ln
import kotlin.random.Random

@Aspect
@Component
class PerCacheAspect(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) {
    @Around("@annotation(perCacheable)")
    fun handlePerCache(joinPoint: ProceedingJoinPoint, perCacheable: PerCacheable): Any? {
        val key = perCacheable.key
        val ttl = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS)

        val cachedJson = redisTemplate.opsForValue().get(key) as? String

        if (cachedJson != null && ttl > 0) {
            val wrapperType = object : TypeReference<PerCacheWrapper<List<Post>>>() {}
            val wrapper = objectMapper.readValue(cachedJson, wrapperType)

            val gapScore = wrapper.expiryGapMs * perCacheable.beta * -ln(Random.nextDouble())
            if (gapScore < ttl) {
                return wrapper.data
            }
        }

        val start = System.nanoTime()
        val result = joinPoint.proceed()
        val duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)

        val wrapper = PerCacheWrapper(result, duration)

        val json = objectMapper.writeValueAsString(wrapper)

        redisTemplate.opsForValue().set(
            key,
            json,
            perCacheable.ttlSeconds,
            TimeUnit.SECONDS
        )

        return result
    }

}