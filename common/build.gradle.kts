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