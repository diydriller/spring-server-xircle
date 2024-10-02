package com.xircle.gatewayserver.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val jwtAuthenticationValidateFilter: JwtAuthenticationValidateFilter,
) {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }.httpBasic {
                it.disable()
            }
            .addFilterAt(
                jwtAuthenticationValidateFilter, SecurityWebFiltersOrder.AUTHENTICATION
            )
        return http.build();
    }
}