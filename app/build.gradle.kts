import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("multiplatform") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

repositories {
    mavenCentral()
}

object V {
    const val clikt = "3.5.0"
}

kotlin {

    when (
        System.getProperty("os.name")
            .takeUnless { it.startsWith("Windows") }
            ?: "Windows"
    ) {
        "Mac OS X" -> macosX64("native")
        "Linux" -> linuxX64("native")
        "Windows" -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }.apply {
        binaries {
            executable {
                entryPoint = "io.github.vantoozz.maze.main"
            }
        }
    }

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.github.ajalt.clikt:clikt:${V.clikt}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }

    jvmToolchain {
        languageVersion
            .set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    isZip64 = true
}

application {
    mainClass.set("io.github.vantoozz.maze.AppKt")
}
