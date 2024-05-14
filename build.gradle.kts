plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.aaleksiev.validate.credit.card"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}