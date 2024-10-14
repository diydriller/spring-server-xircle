package com.xircle.apiserver.config

import com.xircle.apiserver.security.AuthenticationFilter
import com.xircle.apiserver.security.SecurityExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationFilter: AuthenticationFilter,
    private val securityExceptionHandler: SecurityExceptionHandler
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/api/member",
                        "/api/login"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(securityExceptionHandler)
                it.accessDeniedHandler(securityExceptionHandler)
            }
        return http.build()
    }
}