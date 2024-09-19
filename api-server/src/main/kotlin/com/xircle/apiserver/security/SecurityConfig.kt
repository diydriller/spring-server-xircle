package com.xircle.apiserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.apiserver.web.TokenProvider
import com.xircle.core.store.member.MemberStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val memberDetailService: MemberDetailService,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val memberStore: MemberStore,
    private val objectMapper: ObjectMapper,
    private val baseAuthenticationEntryPoint: BaseAuthenticationEntryPoint,
    private val baseAccessDeniedHandler: BaseAccessDeniedHandler
) {
    @Bean
    fun filterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.formLogin {
                it.disable()
            }.httpBasic {
                it.disable()
            }.addFilter(
                JwtAuthenticationAttemptFilter(
                    authenticationManager, memberStore, tokenProvider, objectMapper
                )
            ).addFilter(
                JwtAuthenticationValidateFilter(
                    authenticationManager, memberStore, tokenProvider
                )
            ).authorizeHttpRequests {
                it.requestMatchers("/member", "/login").permitAll()
                    .anyRequest().authenticated()
            }.exceptionHandling {
                it.authenticationEntryPoint(baseAuthenticationEntryPoint)
                it.accessDeniedHandler(baseAccessDeniedHandler)
            }
        return http.build()
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authManagerBuilder.userDetailsService(memberDetailService).passwordEncoder(passwordEncoder)
        return authManagerBuilder.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}