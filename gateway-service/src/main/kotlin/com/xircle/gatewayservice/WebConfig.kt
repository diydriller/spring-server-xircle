package com.xircle.gatewayservice

import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfig {
    @Bean
    fun feignHttpMessageConverters(): HttpMessageConverters {
        return HttpMessageConverters()
    }
}