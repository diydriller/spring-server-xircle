package com.xircle.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.*

@Configuration
class RedisConfig(
    @Value("\${redis.host}")
    private val redisSourceHost: String,
    @Value("\${redis.port}")
    private val redisSourcePort: Int,
    @Value("\${redis.password}")
    private val redisSourcePassword: String
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisConfiguration = RedisStandaloneConfiguration()
        redisConfiguration.hostName = redisSourceHost
        redisConfiguration.port = redisSourcePort
        redisConfiguration.setPassword(redisSourcePassword)
        val lettuceConnectionFactory = LettuceConnectionFactory(redisConfiguration)
        return lettuceConnectionFactory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer(redisObjectMapper())
        redisTemplate.setEnableTransactionSupport(true)
        return redisTemplate
    }

    @Bean
    fun redisCacheManager(): CacheManager {
        val cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
            )

        return RedisCacheManager.builder(redisConnectionFactory())
            .cacheDefaults(cacheConfig)
            .build()
    }

    private fun redisObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.activateDefaultTyping(
            BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Any::class.java)
                .build(),
            ObjectMapper.DefaultTyping.NON_FINAL
        )
        return objectMapper
    }

    @Bean
    fun redisMessageListenerContainer(redisConnectionFactory: RedisConnectionFactory?): RedisMessageListenerContainer {
        val redisMessageListenerContainer = RedisMessageListenerContainer()
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory!!)
        return redisMessageListenerContainer
    }
}