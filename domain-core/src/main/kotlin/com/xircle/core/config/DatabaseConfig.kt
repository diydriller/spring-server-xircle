package com.xircle.core.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.xircle.core.repository"])
@EntityScan(basePackages = ["com.xircle.core.domain"])
@Configuration
class DatabaseConfig {
}