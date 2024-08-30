plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "xircle"
include("api-server")
include("domain-core")
include("common")
