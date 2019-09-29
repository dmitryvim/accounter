plugins {
    kotlin("jvm") version "1.3.50"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.javalin:javalin:3.5.0")
    implementation("org.slf4j:slf4j-simple:1.7.26")
    implementation("com.github.pengrad:java-telegram-bot-api:4.4.0")

    testImplementation(kotlin("test-junit"))
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.dmitryvim.accounter.MainKt"
    }
}