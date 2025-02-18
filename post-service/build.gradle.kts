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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.resilience4j:resilience4j-spring-boot3")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
}

dependencyManagement {
    imports {
        mavenBom ("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("diydriller/${rootProject.name}-${project.name}")
}

