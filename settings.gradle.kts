plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "xircle"
include("api-server")
include("domain-core")
include("common")
include("notification-server")
include("gateway-server")
include("discovery-server")
include("infrastructure-jpa")
include("infrastructure-redis")
