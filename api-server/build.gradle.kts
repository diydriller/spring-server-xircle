tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

ext {
    set("springCloudVersion", "2023.0.3")
}

dependencies {
    implementation(project(":domain-core"))
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.3.3")
    implementation("com.auth0:java-jwt:4.4.0")
    testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
    imports {
        mavenBom ("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}