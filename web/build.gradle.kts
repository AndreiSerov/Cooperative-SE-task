plugins {
    kotlin("jvm")
    war
    id("org.gretty") version "3.0.2"
    id ("com.heroku.sdk.heroku-gradle") version "1.0.4"
}

gretty {
    contextPath = '/'
}

val staging by configurations.creating

dependencies {
    staging("com.heroku:webapp-runner-main:9.0.31.0")

    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.servlet:javax.servlet-api:4.0.1")
}

// Heroku
tasks {
    val copyToLib by registering(Copy::class) {
        into("$buildDir/server")
        from(staging) {
            include("webapp-runner*")
        }
    }
    val stage by registering {
        dependsOn("build", copyToLib)
    }
}