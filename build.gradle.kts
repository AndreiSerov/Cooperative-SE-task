/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds
 */

repositories {
    jcenter()
    maven {
        url = uri("https://kotlin.bintray.com/kotlinx")
    }
}

plugins {
    kotlin("jvm") version "1.3.71"
    id("org.flywaydb.flyway") version "6.3.2"

    // app
    application
}

dependencies {
    // main
    implementation(kotlin("stdlib-jdk7"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.2.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.flywaydb:flyway-core:6.3.3")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
    implementation("org.apache.logging.log4j:log4j-api:2.13.1")
    implementation("org.apache.logging.log4j:log4j-core:2.13.1")
    // test
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:latest.release")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:latest.release")
    testImplementation(kotlin("kotlin-test"))
    testImplementation(kotlin("kotlin-test-junit"))
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.dinosaur.app.MainKt"
    }
}

apply {
    application
}

application {
    mainClassName = "com.dinosaur.app.MainKt"
    applicationName = "AppKT"
}

