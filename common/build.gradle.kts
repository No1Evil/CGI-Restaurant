import com.google.protobuf.gradle.id

plugins {
    id("com.google.protobuf") version "0.9.6"
}

dependencies {

    implementation("com.google.protobuf:protobuf-java:4.34.0")
    implementation("io.grpc:grpc-netty-shaded:1.79.0")
    implementation("io.grpc:grpc-stub:1.79.0")
    implementation("io.grpc:grpc-protobuf:1.79.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// https://medium.com/@rajumanoj/protobuf-installation-for-grpc-gradle-a063d69214c1
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.29.3"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.79.0"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc") { }
            }
        }
    }
}