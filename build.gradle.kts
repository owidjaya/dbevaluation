import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.8.21" // The Kotlin Spring plugin
}

group = "com.oscar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // spring boot
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(platform("org.springframework.data:spring-data-bom:2022.0.3"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.0.4")

    // database
    implementation("org.postgresql:r2dbc-postgresql:1.0.1.RELEASE")
    implementation("io.lettuce:lettuce-core:6.2.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-actuator:3.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.0-Beta")
    implementation("org.danbrough.kotlinx:kotlinx-coroutines-reactor:1.6.4")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
        jvmTarget.set(JvmTarget.JVM_17)
    }
}