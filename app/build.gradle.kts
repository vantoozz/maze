plugins {
    kotlin("jvm") version "1.7.20"
    application
}

repositories {
    mavenCentral()
}

object V {
    const val kli = "0.0.1"
    const val logback = "1.2.11"
    const val slf4j = "1.7.36"
    const val junit = "5.9.1"
}

dependencies {
    implementation(kotlin("bom"))

    implementation("org.slf4j:slf4j-api:${V.slf4j}")
    implementation("ch.qos.logback:logback-core:${V.logback}")
    implementation("ch.qos.logback:logback-classic:${V.logback}")

    implementation("io.github.vantoozz.kli:runner:${V.kli}")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:${V.junit}")
}

application {
    mainClass.set("io.github.vantoozz.maze.AppKt")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

kotlin {
    jvmToolchain {
        languageVersion
            .set(JavaLanguageVersion.of(11))
    }
}
