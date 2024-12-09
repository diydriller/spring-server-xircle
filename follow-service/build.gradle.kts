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
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.3.3")
    implementation("org.springframework.boot:spring-boot-starter-data-neo4j")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
}

dependencyManagement {
    imports {
        mavenBom ("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}