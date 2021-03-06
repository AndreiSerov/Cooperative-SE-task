/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds
 */

repositories {
    maven {
        url = uri("https://kotlin.bintray.com/kotlinx")
    }
}

plugins {
    kotlin("jvm")
    id("org.flywaydb.flyway") version "6.3.2"
    jacoco
}

jacoco {
    toolVersion = "0.8.5"
    reportsDir = file("$buildDir/reports/jacoco")
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
    // guice
    implementation("com.google.inject:guice:4.2.3")
    implementation("com.google.inject.extensions:guice-servlet:4.2.3")
    // test
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:latest.release")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:latest.release")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:latest.release")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))



}

tasks {
    build {
        dependsOn(fatJar)
    }

    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
        testLogging {
            events("passed", "failed")
        }
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.destination = file("$buildDir/reports/jacoco/html")
        }
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = "0.5".toBigDecimal()
                }
            }
        }
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.dinosaur.app.MainKt"
    }
    from (configurations
            .runtimeClasspath
            .get()
            .map { if (it.isDirectory) it else zipTree(it) }
    )
    with(tasks.jar.get() as CopySpec)
}