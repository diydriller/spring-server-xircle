package com.xircle.core.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(
    value = [
        "application-core.yml",
        "application-core-\${spring.profiles.active}.yml"
    ], factory = YamlLoadFactory::class
)
class PropertiesConfig