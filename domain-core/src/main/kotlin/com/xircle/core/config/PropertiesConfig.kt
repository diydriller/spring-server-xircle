package com.xircle.core.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(
    value = [
        "classpath:application-core.yml",
        "classpath:application-core-\${spring.profiles.active}.yml"
    ], factory = YamlLoadFactory::class
)
class PropertiesConfig