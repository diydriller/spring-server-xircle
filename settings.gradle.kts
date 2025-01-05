plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "xircle"
include("common")
include("gateway-service")
include("discovery-service")
include("user-service")
include("post-service")
include("follow-service")
include("event-service")
include("config-service")
include("chat-service")
