package com.xircle.postservice.infrastructure.cache

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PerCacheable(
    val key: String,
    val ttlSeconds: Long = 300,
    val beta: Double = 1.0
)
