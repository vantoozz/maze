plugins {
    id("com.gradle.enterprise") version ("3.11.4")
}

rootProject.name = "maze"
include("app")

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
