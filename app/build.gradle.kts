plugins {
    kotlin("jvm") version "1.7.21"
    application
}

repositories {
    mavenCentral()
}

object V {
    const val clikt = "3.5.0"
    const val junit = "5.9.1"
}

dependencies {
    implementation(kotlin("bom"))

    implementation("com.github.ajalt.clikt:clikt:${V.clikt}")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:${V.junit}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${V.junit}")
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
