val springGrpcVersion by extra("1.0.2")

plugins {
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
}

description = "web-app"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:$springGrpcVersion")
    }
}

dependencies {
    implementation(project(":common"))

    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.grpc:spring-grpc-client-spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
