plugins {}

subprojects {
    plugins.apply("java")

    group = "org.kindness"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        options.release.set(25)
        options.encoding = "UTF-8"
    }

    dependencies {
    }
}