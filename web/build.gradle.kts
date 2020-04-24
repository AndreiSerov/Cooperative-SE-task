plugins {
    kotlin("jvm")
    war
    id("org.gretty") version "3.0.2"
}

gretty {
    contextPath = '/'
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.servlet:javax.servlet-api:4.0.1")
}