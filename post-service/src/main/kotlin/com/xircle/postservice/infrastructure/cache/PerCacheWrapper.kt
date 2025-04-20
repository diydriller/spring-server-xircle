package com.xircle.postservice.infrastructure.cache

data class PerCacheWrapper<T>(
    val data: T,
    val expiryGapMs: Long
)