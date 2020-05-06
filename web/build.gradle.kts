plugins {
    kotlin("jvm")
    war
    id("org.gretty") version "3.0.2"
    id ("com.heroku.sdk.heroku-gradle") version "1.0.4"
}

gretty {
    contextPath = "/"
    servletContainer = "jetty9.4"
}

val staging by configurations.creating

dependencies {
    staging("org.eclipse.jetty:jetty-runner:9.4.28.v20200408")

    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.inject:guice:4.2.3")
    implementation("com.google.inject.extensions:guice-servlet:4.2.3")
    providedCompile("javax.servlet:javax.servlet-api:4.0.1")
    //logger
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
    implementation("org.apache.logging.log4j:log4j-api:2.13.1")
    implementation("org.apache.logging.log4j:log4j-core:2.13.1")
}

// Heroku
tasks {
    val copyToLib by registering(Copy::class) {
        into("$buildDir/server")
        from(staging) {
            include("jetty-runner*")
        }
    }
    val stage by registering {
        dependsOn("build", copyToLib)
    }
}