buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.50"))
    }
}

plugins {
    kotlin("jvm") version "1.3.50"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.10")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.10")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.10")
    implementation("com.github.pengrad:java-telegram-bot-api:4.4.0")
    implementation("io.javalin:javalin:3.5.0")
    implementation("org.slf4j:slf4j-simple:1.7.26")

    testImplementation(kotlin("test-junit"))
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.dmitryvim.accounter.MainKt"
    }

    from(Callable { configurations["runtimeClasspath"].map { if (it.isDirectory) it else zipTree(it) } })
}
