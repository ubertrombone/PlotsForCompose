@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "PlotsForCompose"

include(":plots-for-compose")
include(":app:common")
include(":app:android")
include(":app:desktop")
