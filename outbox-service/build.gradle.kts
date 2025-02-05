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
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.quartz-scheduler:quartz:2.5.0")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
}

dependencyManagement {
    imports {
        mavenBom ("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
